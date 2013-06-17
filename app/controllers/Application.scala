package controllers;

import play.api.Play.current
import play.api.mvc._
import securesocial.core.{SecureSocial, Identity, Authorization}
import play.Logger
import play.libs.Akka
import akka.actor.{Actor, Props}
import models._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders.ObjectId
import play.api.data.Form
import play.api.data.Forms._
import java.util.Date
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits._
import com.mongodb.casbah.commons.MongoDBObject
import views.html

object Application extends Controller with SecureSocial{

  def index = SecuredAction { implicit request =>
    implicit val user = request.user
    val posts = Post.findShares()
    val contacts = User.findContactUser
    val recommendUsers = User.findOtherUsers
    Ok(html.index(user,"",posts,contacts,recommendUsers))
  }

}


case class WithProvider(provider: String) extends Authorization {
	  def isAuthorized(user: Identity) = {
	    user.id.providerId == provider
	  }
}