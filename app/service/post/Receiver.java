package service.post;

import akka.actor.ActorRef;
import akka.actor.Props;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Akka;
import play.libs.Json;
import redis.clients.jedis.JedisPubSub;

public class Receiver extends JedisPubSub {



    @Override
    public void onMessage(String channel, String message) {
        if(channel.contains("_")){
            ActorRef postActor = Akka.system().actorOf(new Props(StreamActor.class));
            JsonNode newPost = Json.parse(message);
            ((ObjectNode)newPost).put(StreamActor.CHANNEL_FIELD, channel);
            StreamActor.instance.tell(newPost);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String s, int i) {

    }

    @Override
    public void onUnsubscribe(String s, int i) {

    }

    @Override
    public void onPUnsubscribe(String s, int i) {

    }

    @Override
    public void onPSubscribe(String s, int i) {

    }

}
