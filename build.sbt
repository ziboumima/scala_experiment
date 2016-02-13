name := """hello"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.7" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.1"

scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions"
)