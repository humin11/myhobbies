import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "MyHobbies"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.mindrot" % "jbcrypt" % "0.3m"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here  
      resolvers += "jbcrypt repo" at "http://mvnrepository.com/"
  )

}
