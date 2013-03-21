package models;

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class User(email: String, name: String, password: String)

object User {
  val simple = {
      get[String]("user.email") ~
      get[String]("user.name") ~
      get[String]("user.password") map {
        case email ~ name ~ password  => User(email, name, password)
      }
  }
}

