package controllers

import play.api.mvc.Controller
import play.api.Play.current
import play.api._
import play.api.mvc._
import data._
import models._
import com.mongodb.casbah.commons.MongoDBObject

object Notifications extends Controller {

  def list = Action { request=>
    implicit val user = User.findOne(MongoDBObject("name" -> "admin")).get
    Ok(views.html.post.messagebox(Notification.unreadByUser))
  }

}
