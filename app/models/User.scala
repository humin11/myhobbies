package models;

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

case class User(
  id: ObjectId = new ObjectId,
  email: Option[String] = None,
  name: String,
  password: Option[String] = None
)

object User extends ModelCompanion[User, ObjectId]{

  val collection = mongoCollection("users")
  val dao = new SalatDAO[User, ObjectId](collection = collection) {}

  def findContactUser(implicit user: User) = Contact.findByOwner.map{ contact => findOneById(contact.person).get }

  def findOtherUsers(implicit user: User) = {
    val contacts = findContactUser
    findAll().filterNot { user1 =>
      user1.equals(user) || contacts.contains(user1)
    }.toSeq
  }

  def findSample = findOne(MongoDBObject("name" -> "admin")).get

}

