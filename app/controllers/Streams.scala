package controllers;

import play.api.mvc._
import models._
import views._
import com.mongodb.casbah.commons.MongoDBObject
import service._
import play.api.libs.iteratee.Enumerator
import java.util.concurrent.TimeUnit
import akka.pattern.ask
import akka.util.Timeout
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.Comet


object Streams extends Controller {
  
  def list = Action { request =>
    implicit val user = {User.findOne(MongoDBObject("name" -> "admin")).get}
    val posts = Post.findShares()
    Ok(html.index("",posts))
  }

  def comet = Action { request =>
    AsyncResult {
      implicit val user = {User.findOne(MongoDBObject("name" -> "admin")).get}
      val cometJS = request.getQueryString("callback").getOrElse("")
      for(followed <- User.findContactUser){
        Redis.subscribe("user_"+user.id)
      }
      implicit val timeout = Timeout(5,TimeUnit.SECONDS)
      val cometFuture = (CometActor.ref ? Connect(user,cometJS)).mapTo[Enumerator[String]]
      cometFuture.map { chunks =>
        Console.println("***********"+chunks)
        Ok.stream(chunks >>> Enumerator.eof).as(JAVASCRIPT)
      }
    }
  }

  def markread = Action { request =>
    Ok
  }

}
