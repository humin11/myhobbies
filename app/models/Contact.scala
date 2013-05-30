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

case class Contact (
  id: ObjectId = new ObjectId,
  owner: UserId,
  person: UserId,
  create_at: Date
)

object Contact extends ModelCompanion[Contact, ObjectId]{

  def collection = mongoCollection("contacts")
  val dao = new SalatDAO[Contact, ObjectId](collection = collection) {}

  collection.ensureIndex(MongoDBObject("owner" -> 1,"person" -> 2), "contact_person", unique = true)

  def findByOwner(implicit owner: User):Seq[Contact] = find(MongoDBObject("owner" -> owner.id)).toSeq

  def findByPerson(implicit person: User):Seq[Contact] = find(MongoDBObject("person" -> person.id)).toSeq

  def findPersonByOwner(personId: ObjectId)(implicit owner: User) = findOne(MongoDBObject("owner" -> owner.id,"person" -> personId))

}
