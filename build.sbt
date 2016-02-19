name := """hello"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.7" % "test"

scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions"
)

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.0"