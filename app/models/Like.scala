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
import mongoContext._
import com.mongodb.casbah.commons.TypeImports._

case class Like(
  id: ObjectId = new ObjectId,
  author: ObjectId,
  source_id: ObjectId,
  source_type: String = "POST",
  create_at: Date,
  update_at: Date
)

object Like extends ModelCompanion[Like, ObjectId]{

  val collection = mongoCollection("likes")
  val dao = new SalatDAO[Like, ObjectId](collection = collection) {}

  def counts(postId:ObjectId,sourceType:String = "POST") = count(MongoDBObject("source_id" -> postId, "source_type" -> sourceType))

  def exists(sourceId:ObjectId,sourceType:String = "POST")(implicit user: User) = findOne(
    MongoDBObject("source_id" -> sourceId, "source_type" -> sourceType, "author" -> user.id)
  ) match {
    case Some => true
    case None => false
  }


}
