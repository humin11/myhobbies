package models;

import play.api.Play.current
import java.util.Date
import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.TypeImports.ObjectId
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import se.radley.plugin.salat._
import securesocial.core._
import mongoContext._
import org.joda.time.DateTime

case class Comment(
  id: ObjectId = new ObjectId,
  source_id: ObjectId,
  source_type: String = "POST",
  author: UserId,
  content: String,
  status: String = "NEW",
  create_at: DateTime,
  update_at: DateTime
)

object Comment extends ModelCompanion[Comment, ObjectId]{

  val collection = mongoCollection("comments")
  val dao = new SalatDAO[Comment, ObjectId](collection = collection) {}

  def findBySource(source_id:ObjectId,source_type:String="POST") = find(MongoDBObject("source_id" -> source_id, "source_type" -> source_type)).toSeq

}
