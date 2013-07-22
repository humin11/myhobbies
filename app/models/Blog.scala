package models

import play.api.Play.current
import java.util.Date
import com.novus.salat.annotations._
import se.radley.plugin.salat.Binders._
import org.bson.types.ObjectId
import se.radley.plugin.salat.Binders.ObjectId
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import se.radley.plugin.salat._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import securesocial.core._
import mongoContext._
import play.api.libs.json._
import utils.formaters.ObjectIdFormatter._
import org.joda.time.DateTime

case class Blog(
  id: ObjectId = new ObjectId,
  author: UserId,
  content: String,
  raw_text: String,
  create_at: DateTime,
  update_at: DateTime,
  locked: Boolean = false
)

object Blog extends BlogDAO with BlogJson

trait BlogDAO extends ModelCompanion[Blog, ObjectId]{

  val collection = mongoCollection("blogs")
  val dao = new SalatDAO[Blog, ObjectId](collection = collection) {}

}

trait BlogJson {

  implicit val blogJsonWrite = new Writes[Blog] {
    def writes(blog: Blog): JsValue = {
      Json.obj(
        "id" -> blog.id,
        "author" -> blog.author.id,
        "content" -> blog.content,
        "raw_text" -> blog.raw_text,
        "create_at" -> blog.create_at,
        "update_at" -> blog.update_at
      )
    }
  }
}
