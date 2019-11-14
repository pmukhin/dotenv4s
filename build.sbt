import Dependencies._

version := "0.0.1"

lazy val contributors = Seq(
  "pmukhin" -> "Pavel Mukhin",
)

lazy val commonSettings = Seq(
  organization := "io.github.pmukhin",
  scalaVersion := "2.13.0",
  crossScalaVersions := Seq(scalaVersion.value, "2.12.8"),
  libraryDependencies ++= scalaTest
)

lazy val root = project
  .in(file("."))
  .aggregate(core, `cats-effect`)
  .settings(noPublishSettings)
  .settings(commonSettings,  releaseSettings)

lazy val core = project
  .settings(commonSettings)
  .in(file("core"))
  .settings(
    name := "dotenv4s-core",
    libraryDependencies += javaJna
  )

lazy val examples = project
  .settings(commonSettings)
  .in(file("examples"))
  .settings(name := "dotenv4s-examples")
  .dependsOn(core)

lazy val `cats-effect` = project
  .settings(commonSettings)
  .in(file("cats-effect"))
  .dependsOn(core)
  .settings(
    name := "dotenv4s-cats-effect",
    libraryDependencies ++= Seq(catsEffect)
  )

lazy val releaseSettings = {
  Seq(
    publishArtifact in Test := false,
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/pmukhin/dotenv4s"),
        "git@github.com:pmukhin/dotenv4s.git"
      )
    ),
    homepage := Some(url("https://github.com/pmukhin/dotenv4s")),
    licenses := Seq(
      "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")
    ),
    publishMavenStyle := true,
    pomIncludeRepository := { _ =>
      false
    },
    pomExtra := {
      <developers>
        {for ((username, name) <- contributors) yield
        <developer>
          <id>{username}</id>
          <name>{name}</name>
          <url>http://github.com/{username}</url>
        </developer>
        }
      </developers>
    }
  )
}

lazy val noPublishSettings = {
  Seq(publish := {}, publishLocal := {}, publishArtifact := false)
}
