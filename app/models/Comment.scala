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

case class Comment(
  id: ObjectId = new ObjectId,
  post: ObjectId,
  author: UserId,
  content: String,
  status: String = "NEW",
  create_at: Date,
  update_at: Date
)

object Comment extends ModelCompanion[Comment, ObjectId]{

  val collection = mongoCollection("comments")
  val dao = new SalatDAO[Comment, ObjectId](collection = collection) {}

  def findByPost(postId:ObjectId) = find(MongoDBObject("post" -> postId)).toSeq

}
