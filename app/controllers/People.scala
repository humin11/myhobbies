package controllers

import play.api.mvc.{Action, Controller}
import models.{Contact, User}
import com.mongodb.casbah.commons.MongoDBObject
import java.util.Date
import org.bson.types.ObjectId
import securesocial.core._
import play.Logger
import org.joda.time.DateTime

object People extends Controller with SecureSocial{

  def avatar = SecuredAction { request =>
    implicit val user = request.user
    val personId = request.getQueryString("identityId").getOrElse("")
    val followed = Contact.findPersonByOwner(personId).isDefined;
    val person = User.findOneByStringId(personId).get
    val myself = user.identityId==person.identityId
    Ok(views.html.post.avatarModal(person,followed,myself))
  }

  def follow = SecuredAction { request =>
    implicit val user = request.user
    val now = DateTime.now()
    val personId = request.getQueryString("identityId").getOrElse("")
    val person = User.findOneByStringId(personId).get.identityId
    Contact.findPersonByOwner(personId) match {
      case None => Contact.save(Contact(owner = user.identityId,person = person,create_at = now))
      case _ =>
    }
    Ok
  }

  def unfollow = SecuredAction { request =>
    implicit val user = request.user
    val personId = request.getQueryString("identityId").getOrElse("")
    Contact.findPersonByOwner(personId) map { contact =>
      Contact.remove(contact)
    }
    Ok
  }

}
