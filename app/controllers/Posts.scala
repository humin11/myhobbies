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
import scala.util.Random

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
          source_id = post.id,
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
    Photo.removeByPost(new ObjectId(id))
    Post.removeById(new ObjectId(id))
    Ok
  }

  def uploadify = Action(parse.multipartFormData) { request =>
    val pic = request.body.files(0)
    val ext = pic.filename.drop(pic.filename.lastIndexOf(".")).toLowerCase
    val filename = pic.filename.substring(0,pic.filename.lastIndexOf("."))+System.currentTimeMillis()+Random.nextInt(1000)
    val tmpfile = new File("public"+File.separator+"tmp"+File.separator+filename+ext)
    pic.ref.moveTo(tmpfile)
    Ok("/assets/tmp/"+filename+ext)
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

  def like = Action { request =>
    implicit val user = User.findOne(MongoDBObject("name" -> "admin")).get
    val id = request.getQueryString("id").getOrElse("")
    val now = new Date()
    Like.findBySource(new ObjectId(id)) match {
      case like:Some[Like] => {
        Like.remove(like.get)
      }
      case None => {
        val like = Like(author = user.id,source_id = new ObjectId(id),create_at = now,update_at = now)
        Like.insert(like)
      }
    }
    Ok(Like.counts(new ObjectId(id)).toString)
  }

  def reshare = Action(parse.urlFormEncoded) { request =>
    implicit val user = User.findOne(MongoDBObject("name" -> "admin")).get
    val id = request.body.get("id").get(0)
    val parentId = Post.findParentId(new ObjectId(id))
    val now = new Date()
    val post = Post(
      author = user.id,
      content = request.body.get("content").get(0),
      create_at = now,
      update_at = now,
      parent = Some(parentId),
      is_reshare = true
    )
    Contact.findByPerson.foreach { contact =>
      val share_visibility = ShareVisibility(
        post = post.id,
        recipient = contact.owner,
        create_at = now,
        update_at = now
      )
      ShareVisibility.insert(share_visibility)
    }
    Post.insert(post)
    Ok(views.html.post.post(post))
  }

}
