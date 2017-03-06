lazy val root = (project in file("."))
  .aggregate(`sbt-play-uibuild`, `play-uibuild`)

lazy val `sbt-play-uibuild`: Project = (project in file("."))
  .settings(
    name := "sbt-play-uibuild",
    scalaVersion := "2.10.6",
    sbtPlugin := true,
    addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.0"),
    addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.3.0"),
    commonSettings
  )

lazy val `play-uibuild`: Project = project.in(file("play-uibuild"))
  .enablePlugins(PlayScala)
  .settings(
    name := "play-uibuild",
    scalaVersion := "2.11.8",
    commonSettings
  )

lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "utf8"),
  javacOptions in Compile ++= Seq("-encoding", "utf8", "-g")
) ++ mavenPublishSettings

lazy val mavenPublishSettings = Seq(
  organization := "com.github.zone24x7",
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://github.com/Zone24x7/sbt-play-uibuild")),
  publishMavenStyle := true,
  publishTo <<= version { (v: String) =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  pomExtra :=
    <scm>
      <url>git@github.com:zone24x7/play-uibuild.git</url>
      <connection>scm:git:git@github.com:zone24x7/sbt-play-uibuild.git</connection>
    </scm>
    <developers>
      <developer>
        <id>Zone24x7</id>
        <name>ZONE24X7, INC.</name>
        <url>https://github.com/Zone24x7</url>
      </developer>
    </developers>
)
