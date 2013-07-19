package controllers

import _root_.models._
import play.api.Play.current
import play.api._
import play.api.mvc._
import data._
import play.api.data.Forms._
import java.util.{Date}
import views._
import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import scala.Some
import java.io.File
import scala.util.Random
import service.Redis
import play.api.libs.json.Json
import utils.formaters.ObjectIdFormatter._
import securesocial.core.SecureSocial
import org.joda.time.DateTime
import java.net.ConnectException

object Blogs extends Controller with SecureSocial{

  def blogkit = Action { request =>
    val id = request.getQueryString("id").getOrElse("")
    Ok(views.html.blog.blogkit(id))
  }

}
