import _root_.sbt.Keys._



name := """hello"""

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"))


val http4sVersion = "0.17.6"


libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.7" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.1"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.1"

libraryDependencies += "io.argonaut" %% "argonaut" % "6.2.1"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.9"

libraryDependencies += "org.scalaz.stream" %% "scalaz-stream" % "0.8.5"

libraryDependencies ++= Seq(
  "com.chuusai"    %% "shapeless"  % "2.3.3",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)


scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0",
  "com.typesafe.play" %% "play-ws" % "2.3.10")
