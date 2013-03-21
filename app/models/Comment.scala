package models;

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Comment(content: String, create_at: Date, update_at: Date)

object Comment {

    val simple = {
      get[String]("comment.content") ~
      get[Date]("comment.create_at") ~
      get[Date]("comment.update_at") map {
        case content ~ create_at ~ update_at  => Comment(content, create_at, update_at)
      }
  }

}
