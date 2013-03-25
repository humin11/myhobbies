package models;

import play.api.Play.current
import java.util.Date

case class Post(content: String, create_at: Date, update_at: Date)

object Post {
  

}
