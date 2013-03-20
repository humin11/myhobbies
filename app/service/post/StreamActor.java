package service.post;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.libs.Akka;
import play.libs.F;
import java.util.ArrayList;
import java.util.List;

public class StreamActor extends UntypedActor {

    public static ActorRef instance = Akka.system().actorOf(new Props(StreamActor.class));

    public final static String CHANNEL_FIELD = "channel";

    List<StreamComet> clients = new ArrayList<StreamComet>();

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof StreamComet){
            final StreamComet cometSocket = (StreamComet)message;
            if(clients.contains(cometSocket)) {
                clients.remove(cometSocket);
                Logger.info("after remove comet:"+clients.size());
            }else{
                cometSocket.onDisconnected(new F.Callback0() {
                    public void invoke() {
                        getContext().self().tell(cometSocket);
                    }
                });
                clients.add(cometSocket);
                Logger.info("after add comet:"+clients.size());
            }
        }else if(message instanceof JsonNode){
            JsonNode newPost = (JsonNode) message;
            for(StreamComet cometSocket: clients) {
                if(newPost.has(CHANNEL_FIELD) && cometSocket.hasChannel(newPost.findValue(CHANNEL_FIELD).asText())){
                    cometSocket.sendMessage(newPost);
                }
            }
        }
    }
}
