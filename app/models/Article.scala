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

case class Article(
  id: ObjectId = new ObjectId,
  author: UserId,
  content: String,
  create_at: Date,
  update_at: Date
)

object Article extends ArticleDAO with ArticleJson

trait ArticleDAO extends ModelCompanion[Article, ObjectId]{

  val collection = mongoCollection("articles")
  val dao = new SalatDAO[Article, ObjectId](collection = collection) {}

}

trait ArticleJson {

  implicit val articleJsonWrite = new Writes[Article] {
    def writes(article: Article): JsValue = {
      Json.obj(
        "id" -> article.id,
        "author" -> article.author.id,
        "content" -> article.content,
        "create_at" -> article.create_at,
        "update_at" -> article.update_at
      )
    }
  }
}
