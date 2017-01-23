import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "de.bookstore",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Scala_JaxEnter_Tutorial",
    logBuffered in Test := false,
    libraryDependencies ++= testDeps
  )