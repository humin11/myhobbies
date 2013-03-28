package controllers;

import play.api.Play.current
import play.api.mvc._
import securesocial.core.{Identity, Authorization}
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

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

}


case class WithProvider(provider: String) extends Authorization {
	  def isAuthorized(user: Identity) = {
	    user.id.providerId == provider
	  }
}