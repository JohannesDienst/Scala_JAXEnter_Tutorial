import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalactic" %% "scalactic" % "3.0.1"
  lazy val scalactic = "org.scalatest" %% "scalatest" % "3.0.1"
  
  val testDeps = Seq(scalaTest, scalactic)
}