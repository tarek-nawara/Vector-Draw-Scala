name := "vector-draw"

organization := "OOP"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test", //http://www.scalatest.org/download
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6"
)

// https://mvnrepository.com/artifact/org.scalafx/scalafx_2.12
libraryDependencies += "org.scalafx" % "scalafx_2.12" % "8.0.102-R11"

shellPrompt := { state => System.getProperty("user.name") + "> " }

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
mainClass in (Compile, run) := Some("edu.paint.ScalaFXHelloWorld")

// Fork a new JVM for 'run' and 'test:run' to avoid JavaFX double initialization problems
fork := true