enablePlugins(ScalaJSPlugin)

name := "HTML DOM and Canvas"
organization := "org.awesome"
version := "1.0"
scalaVersion := "2.13.1"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.8",
  "com.lihaoyi" %%% "scalatags" % "0.7.0"
)
