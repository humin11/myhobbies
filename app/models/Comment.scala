package models;

import play.api.Play.current
import java.util.Date

case class Comment(content: String, create_at: Date, update_at: Date)

object Comment {


}
