import _root_.sbt.Keys._

name := """hello"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.7" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.3"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.1"

libraryDependencies += "io.argonaut" %% "argonaut-scalaz" % "6.2"



scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0",
  "com.typesafe.play" %% "play-ws" % "2.3.10")