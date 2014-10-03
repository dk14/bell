import AssemblyKeys._
import sbt.Package.ManifestAttributes

name := "bell"

version := "1.0"

packageOptions := Seq(ManifestAttributes(
  ("Main-Class", "Launcher")))

assemblySettings