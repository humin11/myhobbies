package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._
import securesocial.core._
import mongoContext._
import org.joda.time.DateTime

case class Contact (
  id: ObjectId = new ObjectId,
  owner: UserId,
  person: UserId,
  create_at: DateTime
)

object Contact extends ModelCompanion[Contact, ObjectId]{

  def collection = mongoCollection("contacts")
  val dao = new SalatDAO[Contact, ObjectId](collection = collection) {}

  collection.ensureIndex(MongoDBObject("owner" -> 1,"person" -> 2), "contact_person", unique = true)

  def findByOwner(implicit owner: Identity):Seq[Contact] = find(MongoDBObject("owner._id" -> owner.id.id)).toSeq

  def findByPerson(implicit person: Identity):Seq[Contact] = find(MongoDBObject("person._id" -> person.id.id)).toSeq

  def findPersonByOwner(personId: String)(implicit owner: Identity) = findOne(MongoDBObject("owner._id" -> owner.id.id,"person._id" -> personId))

}
