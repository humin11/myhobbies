package controllers;

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import java.util.{Date}
import models._
import views._
import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject

object Posts extends Controller {

  def create = Action { request =>
    implicit val user = User.findOne(MongoDBObject("name" -> "admin")).get
    val now = new Date()
    val content = request.getQueryString("content").getOrElse("")
    val post = Post(author = user.id,content = content,create_at = now,update_at = now)
    Contact.findByPerson.foreach{ contact =>
      val share_visibility = ShareVisibility(
        post = post.id,
        recipient = contact.owner,
        create_at = now,
        update_at = now
      )
      ShareVisibility.save(share_visibility)
    }
    val share_visibility = ShareVisibility(post = post.id,recipient = user.id,create_at = now,update_at = now)
    ShareVisibility.save(share_visibility)
    Post.save(post)
    Ok(views.html.post.post(post))
  }

  def delete = Action { request =>
    val postId = request.getQueryString("postId")
    postId map { id =>
      ShareVisibility.removeByPost(new ObjectId(id))
      Post.removeById(new ObjectId(id))
    }
    Ok
  }
  
}
