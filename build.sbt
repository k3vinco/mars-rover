name := "mars-rover"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %%  "monocle-macro"  % "2.0.4",
  "org.scalatest" %% "scalatest" % "3.3.0-SNAP2" % Test
)