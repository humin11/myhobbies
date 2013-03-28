import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "MyHobbies"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
     "org.mindrot" % "jbcrypt" % "0.3m",
    "securesocial" %% "securesocial" % "master-SNAPSHOT",
    "se.radley" %% "play-plugins-salat" % "1.2",
    "com.clever-age" % "play2-elasticsearch" % "0.5.4"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here  
      resolvers += "jbcrypt repo" at "http://mvnrepository.com/",
      resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),
      routesImport += "se.radley.plugin.salat.Binders._",
      templatesImport += "org.bson.types.ObjectId",
      resolvers += Resolver.sonatypeRepo("snapshots"),
      resolvers += Resolver.url("play-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
  )

}
