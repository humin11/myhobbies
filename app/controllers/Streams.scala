package controllers;

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import java.util.{Date}

import models._
import views._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject

object Streams extends Controller {
  
  def list = Action { request =>
    implicit val user = {User.findOne(MongoDBObject("name" -> "admin")).get}
    val posts = Post.findShares()
    Ok(html.index("",posts))
  }
  
  def comet = Action {

    Ok
  }
  
}
