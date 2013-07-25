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
import org.joda.time.DateTime

case class ShareVisibility(
  id: ObjectId = new ObjectId,
  source_id: ObjectId,
  source_type: String = "POST",
  recipient: UserId,
  hidden: Boolean = false,
  create_at: DateTime,
  update_at: DateTime
)

object ShareVisibility extends ModelCompanion[ShareVisibility, ObjectId]{

  val collection = mongoCollection("share_visibilities")
  val dao = new SalatDAO[ShareVisibility, ObjectId](collection = collection) {}

  def share(now:DateTime, source_id:ObjectId, source_type:String = "POST", shareToSelf:Boolean = true)(implicit user:Identity) = {
    Contact.findByPerson(user).foreach { contact =>
      val share_visibility = ShareVisibility(
        source_id = source_id,
        source_type = source_type,
        recipient = contact.owner,
        create_at = now,
        update_at = now
      )
      ShareVisibility.insert(share_visibility)
    }
    if(shareToSelf){
      val share_visibility = ShareVisibility(
        source_id = source_id,
        source_type = source_type,
        recipient = user.id,
        create_at = now,
        update_at = now
      )
      ShareVisibility.insert(share_visibility)
    }
  }

  def findShares(pageNum:Int = 0, maxRow:Int = 15)(implicit user:Identity) = {
    ShareVisibility.find(MongoDBObject("recipient._id" -> user.id.id, "hidden" -> false))
      .sort(MongoDBObject("create_at" -> -1))
      .skip(pageNum*maxRow)
      .limit(maxRow)
      .map{ shareVisibility =>
        shareVisibility.source_type match {
          case "POST" => Post.findOneById(shareVisibility.source_id)
          case "BLOG" => Blog.findOneById(shareVisibility.source_id)
        }
      }
      .filter(_.isDefined).map(_.get).toSeq
  }

  def removeByPost(postId:ObjectId) = find(MongoDBObject("source_id" -> postId,"source_type" -> "POST")).foreach(ShareVisibility.remove(_))

}
