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
import securesocial.core.SecureSocial
import scala.concurrent._


object Streams extends Controller with SecureSocial {

  def comet = UserAwareAction { request =>
    AsyncResult {
      request.user match {
        case Some(user) => {
          val cometJS = request.getQueryString("callback").getOrElse("")
          for(followed <- User.findContactUser(user)){
            Redis.subscribe("user_"+user.identityId)
          }
          implicit val timeout = Timeout(5,TimeUnit.SECONDS)
          val cometFuture = (CometActor.ref ? Connect(user,cometJS)).mapTo[Enumerator[String]]
          cometFuture.map { chunks =>
            Console.println("***********"+chunks)
            Ok.stream(chunks >>> Enumerator.eof).as(JAVASCRIPT)
          }
        }
		case None => Future.successful(NotFound)
      }

    }
  }

  def markread = Action { request =>
    Ok
  }

}
