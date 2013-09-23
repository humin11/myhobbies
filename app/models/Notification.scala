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
import utils.formaters.ObjectIdFormatter._
import org.joda.time.DateTime

case class Notification(
  id: ObjectId = new ObjectId,
  source_id: ObjectId,
  source_type: String = "COMMENT",
  recipient: IdentityId,
  unread: Boolean = true,
  create_at: DateTime,
  update_at: DateTime
)

object Notification extends ModelCompanion[Notification, ObjectId]{

  val collection = mongoCollection("notifications")
  val dao = new SalatDAO[Notification, ObjectId](collection = collection) {}

  def unreadByUser(implicit user:User) = find(MongoDBObject("unread" -> true,"recipient" -> user.identityId)).toSeq

}