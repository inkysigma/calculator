name := "CalculatorProject"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "org.jfree" % "jfreechart" % "1.5.0"
libraryDependencies += "org.jfree" % "jfreechart-fx" % "1.0.0"
libraryDependencies += "com.jfoenix" % "jfoenix" % "1.10.0"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.4"