package models;

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Post(content: String, create_at: Date, update_at: Date)

object Post {
  
  val simple = {
      get[String]("post.content") ~
      get[Date]("post.create_at") ~
      get[Date]("post.update_at") map {
        case content ~ create_at ~ update_at  => Post(content, create_at, update_at)
      }
  }

}
