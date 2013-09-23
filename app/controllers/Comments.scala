package controllers;

import _root_.models._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import utils.ordering.JodaDateTimeOrdering._
import java.util.{Date}

import views._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.TypeImports.ObjectId
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.TypeImports.ObjectId
import securesocial.core._
import org.joda.time.DateTime


object Comments extends Controller with SecureSocial{

  def create = UserAwareAction(parse.urlFormEncoded) { request =>
    request.user match {
      case Some(user) => {
        val now = DateTime.now()
        val source_id = request.body.get("source_id").get(0)
        val source_type = request.body.get("source_type") match {
          case s:Some[Seq[String]] => s.get(0)
          case None => "POST"
        }
        val content = request.body.get("content").get(0)
        val comment = Comment(
          source_id = new ObjectId(source_id),
          source_type = source_type,
          author = user.identityId,
          content = content,
          create_at = now,
          update_at = now
        )
        Post.findOneById(new ObjectId(source_id)) match {
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
        Ok(html.post.comment(user,comment))
      }
      case _ => Ok
    }
  }
    
  def delete = Action { request =>
    val id = request.getQueryString("id").getOrElse("")
    Comment.removeById(new ObjectId(id))
    Ok
  }

  def list = SecuredAction { request =>
    val source_id = request.getQueryString("source_id").getOrElse("")
    val source_type = request.getQueryString("source_type").getOrElse("POST")
    val show = request.getQueryString("show").getOrElse("")
    val comments = Comment.findBySource(new ObjectId(source_id),source_type)
    val result = if(show == "all"){
      for(comment <- comments) yield html.post.comment(request.user,comment)
    }else{
      for(comment <- comments.sortBy(_.create_at).takeRight(show.toInt)) yield html.post.comment(request.user,comment)
    }
    Ok(result.reduceLeft(_ += _))
  }


}
