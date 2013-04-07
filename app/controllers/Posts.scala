package controllers;

import play.api.Play.current
import play.api._
import play.api.mvc._
import data._
import play.api.data.Forms._
import java.util.{Date}
import models._
import views._
import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import java.util
import scala.Some
import java.io.File

object Posts extends Controller{

  def create = Action(parse.urlFormEncoded) { implicit request =>
    implicit val user = User.findOne(MongoDBObject("name" -> "admin")).get
    val now = new Date()
    val post = Post(author = user.id,content = request.body.get("content").get(0),create_at = now,update_at = now)
    Contact.findByPerson.foreach { contact =>
      val share_visibility = ShareVisibility(
        post = post.id,
        recipient = contact.owner,
        create_at = now,
        update_at = now
      )
      ShareVisibility.insert(share_visibility)
    }
    val share_visibility = ShareVisibility(post = post.id,recipient = user.id,create_at = now,update_at = now)
    ShareVisibility.insert(share_visibility)
    request.body.get("tmpfiles[]").map { pics =>
      pics.foreach { pic =>
        val photo = Photo(
          source = post.id,
          author = user.id,
          path = pic,
          name = "",
          create_at = now,
          update_at = now
        )
        Photo.insert(photo)
      }
    }
    Post.insert(post)
    Ok(views.html.post.post(post))
  }

  def delete = Action { request =>
    val id = request.getQueryString("id").getOrElse("")
    ShareVisibility.removeByPost(new ObjectId(id))
    Photo.removeBySource(new ObjectId(id))
    Post.removeById(new ObjectId(id))
    Ok
  }

  def uploadify = Action(parse.multipartFormData) { request =>
    val pic = request.body.files(0)
    val ext =pic.filename.drop(pic.filename.indexOf("."))
    val tmpfile = new File("public"+File.separator+"tmp"+File.separator+pic.ref.file.getName+ext)
    pic.ref.moveTo(tmpfile)
    Ok("/assets/tmp/"+pic.ref.file.getName+ext)
  }

  def deleteTmpFiles = Action(parse.urlFormEncoded) { request =>
    request.body.get("tmpfiles[]").map { pics =>
      pics.foreach { pic =>
        val tmpfile = new File(pic)
        if(tmpfile.exists()){
          tmpfile.delete()
        }
      }
    }
    Ok
  }

}
