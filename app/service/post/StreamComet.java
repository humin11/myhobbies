package service.post;

import models.User;
import play.libs.Comet;
import java.util.List;

public class StreamComet extends Comet {

    private List<String> channels;

    private final User user;

    public StreamComet(String s,User user, List<String> channels) {
        super(s);
        this.user = user;
        this.channels = channels;
    }

    public boolean hasChannel(String channel){
        if(channels != null){
           return channels.contains(channel);
        }
        return false;
    }

    public List<String> getChannels(){
        return channels;
    }

    public User getUser(){
        return user;
    }

    @Override
    public void onConnected() {
        StreamActor.instance.tell(this);
    }

}
