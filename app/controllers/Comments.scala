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

object Comments extends Controller {

  def create = Action { request =>
    val user = {User.findOne(MongoDBObject("name" -> "admin")).get}
    val now = new Date()
    val postId = request.getQueryString("postId").getOrElse("")
    val content = request.getQueryString("content").getOrElse("")
    val comment = Comment(post = new ObjectId(postId),author = user.id,content = content,create_at = now,update_at = now)
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
