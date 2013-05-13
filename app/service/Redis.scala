package service

import com.redis.RedisClientPool
import org.codehaus.jackson.JsonNode
import akka.pattern.ask

object Redis {

  val clients = new RedisClientPool("localhost", 6379)

  def publish(channel: String,data: JsonNode) = {
    clients.withClient {
      client => {
        client.publish(channel,data.toString)
        client.disconnect
      }
    }
  }

  def subscribe(channel: String) = {
    clients.withClient {
      client => {
        client.subscribe(channel) { message =>

        }
      }
    }
  }

}

object CometActor {
  def act() {

  }
}
