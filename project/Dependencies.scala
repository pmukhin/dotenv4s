import sbt._

object Versions {
  val catsEffectVersion = "2.0.0"
  val scalatestVersion = "3.0.8"
  val jnaVersion = "5.4.0"
}

// noinspection TypeAnnotation
object Dependencies {
  import Versions._

  val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectVersion
  val scalaTest = Seq(
    "org.scalactic" %% "scalactic" % scalatestVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % Test
  )
  val javaJna = "net.java.dev.jna" % "jna" % jnaVersion
}
