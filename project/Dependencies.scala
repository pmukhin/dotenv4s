import sbt._

object Versions {
  val catsEffectVersion = "2.0.0"
  val specs2Version = "4.8.1"
}

object Dependencies {
  import Versions._

  // noinspection TypeAnnotation
  val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectVersion
  val specs2 = "org.specs2" %% "specs2-core" % specs2Version % Test
}
