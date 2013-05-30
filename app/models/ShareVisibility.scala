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

case class ShareVisibility(
  id: ObjectId = new ObjectId,
  post: ObjectId,
  recipient: UserId,
  hidden: Boolean = false,
  create_at: Date,
  update_at: Date
)

object ShareVisibility extends ModelCompanion[ShareVisibility, ObjectId]{

  val collection = mongoCollection("share_visibilities")
  val dao = new SalatDAO[ShareVisibility, ObjectId](collection = collection) {}

  def removeByPost(postId:ObjectId) = find(MongoDBObject("post" -> postId)).foreach(ShareVisibility.remove(_))

}
