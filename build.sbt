name := """play-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.google.code.gson" % "gson" % "2.2",
  "com.typesafe.play.modules" %% "play-modules-redis" % "2.4.1"
)


libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"
libraryDependencies += evolutions
libraryDependencies += filters
resolvers += "google-sedis-fix" at "http://pk11-scratch.googlecode.com/svn/trunk"