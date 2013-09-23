package models

import play.api.Play.current
import java.util.Date
import java.io.File
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


case class Photo (
  id: ObjectId = new ObjectId,
  source_id: ObjectId,
  source_type: String = "POST",
  author: IdentityId,
  path: String,
  name: String,
  create_at: DateTime,
  update_at: DateTime
)

object Photo extends ModelCompanion[Photo, ObjectId]{

  val collection = mongoCollection("photos")
  val dao = new SalatDAO[Photo, ObjectId](collection = collection) {}

  def removeByPost(sourceId:ObjectId) = find(MongoDBObject("source_id" -> sourceId,"source_type" -> "POST")).foreach { photo =>
    val img = new File(photo.path.replaceFirst("/assets","public"))
    if(img.exists()) img.delete()
    Photo.remove(photo)
  }

  def findByPost(sourceId:ObjectId) = find(MongoDBObject("source_id" -> sourceId,"source_type" -> "POST"))
    .filter { photo =>
      new File(photo.path.replaceFirst("/assets","public")).exists
    }.toSeq

}