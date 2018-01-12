import _root_.sbt.Keys._

name := """hello"""

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"


libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.7" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.1"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.1"

libraryDependencies += "io.argonaut" %% "argonaut" % "6.2.1"


libraryDependencies += "org.scalaz.stream" %% "scalaz-stream" % "0.8.5"

scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0",
  "com.typesafe.play" %% "play-ws" % "2.3.10")