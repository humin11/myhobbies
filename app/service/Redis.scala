package service

import play.api.libs.json.{JsValue}
import com.redis._
import play.libs.Akka
import models.User
import akka.actor._
import akka.pattern.ask
import akka.actor._
import play.api.libs.json._
import play.api.libs.iteratee._
import scala.concurrent.duration._
import play.api.libs.iteratee.{Concurrent, Enumerator}
import securesocial.core.Identity
import java.net.{ConnectException, Socket}

object Redis {

  val host:String = "localhost"
  val port:Int = 6379
  lazy val testSocket:Socket = new Socket(host, port)
  lazy val pubClients = new RedisClientPool(host, port)
  lazy val subClients = new RedisClientPool(host, port)

  def connected = {
    try {
      testSocket != null && testSocket.isConnected
    } catch {
      case ex:ConnectException => false
    }
  }

  def publish(channel: String,data: JsValue) = {
    if(connected){
      pubClients.withClient {
        client => {
          client.publish(channel,data.toString())
        }
      }
    }
  }

  def subscribe(channel: String) = {
    if(connected){
      subClients.withClient {
        client => {
          client.subscribe(channel) { message =>
            message match {
              case S(channel, no) =>
              case U(channel, no) =>
              case E(exception) =>
              case M(channel, msg) => {
                CometActor.ref ! PushMessage(msg)
              }
            }
          }
        }
      }
    }
  }
}

class CometActor extends Actor{
  var cometJS = "callback"
  var message = ""
  def receive = {
    case Connect(user,js) => {
      cometJS = js
      while(true){
        if(message.length > 0){
          val tmp = message
          sender ! Enumerator(tmp)
          message = ""
        }
      }
    }
    case Disconnect(user) =>
    case PushMessage(msg) => {
      message = ("""try{"""+cometJS+"""("""+msg+""");}catch(e){}""")
    }
  }
}

object CometActor {

  lazy val ref = Akka.system.actorOf(Props[CometActor], name = "cometActor")

}

case class Connect(user:Identity,cometJS:String)
case class Disconnect(user:Identity)
case class PushMessage(msg:String)
