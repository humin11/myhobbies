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
import com.mongodb.casbah.commons.TypeImports.ObjectId
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.TypeImports.ObjectId
import securesocial.core._

object Comments extends Controller {

  def create = Action(parse.urlFormEncoded) { request =>
    val user = {User.findOne(MongoDBObject("name" -> "admin")).get}
    val now = new Date()
    val postId = request.body.get("postId").get(0)
    val content = request.body.get("content").get(0)
    val comment = Comment(post = new ObjectId(postId),author = user.id,content = content,create_at = now,update_at = now)
    Post.findOneById(new ObjectId(postId)) match {
      case post:Some[Post] => {
        val notification = Notification(
          source_id = comment.id,
          recipient = post.get.author,
          create_at = now,
          update_at = now
        )
        Notification.insert(notification)
      }
      case None =>
    }
    Comment.save(comment)
    Ok(html.post.comment(comment))
  }
    
  def delete = Action { request =>
    val user = {User.findOne(MongoDBObject("name" -> "admin")).get}
    val now = new Date()
    val id = request.getQueryString("id").getOrElse("")
    Comment.removeById(new ObjectId(id))
    Ok
  }

  def list = Action { request =>
    val user = {User.findOne(MongoDBObject("name" -> "admin")).get}
    val now = new Date()
    val id = request.getQueryString("id").getOrElse("")
    val show = request.getQueryString("show").getOrElse("")
    val comments = Comment.findByPost(new ObjectId(id))
    val result = if(show == "all"){
      for(comment <- comments) yield html.post.comment(comment)
    }else{
      for(comment <- comments.sortBy(_.create_at).takeRight(3)) yield html.post.comment(comment)
    }
    Ok(result.reduceLeft(_ += _))
  }


}
