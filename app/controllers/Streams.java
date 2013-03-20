package controllers;

import models.TContact;
import models.TPost;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import service.post.Redis;
import service.post.Receiver;
import service.post.StreamComet;

import java.util.ArrayList;
import java.util.List;

public class Streams extends Controller {

    public static Result list(){
        final User user = Application.getLocalUser(session());
        int pageNum = request().getQueryString("pageNum")==null?1:Integer.parseInt(request().getQueryString("pageNum"));
        int maxRow = request().getQueryString("maxRow")==null?1:Integer.parseInt(request().getQueryString("maxRow"));
        List<TPost> posts = TPost.findPublics(user,pageNum,maxRow);
        return ok();
    }

    public static Result news() {
        final User user = Application.getLocalUser(session());
        if(user == null)
            return ok();
        String cometJS = request().getQueryString("callback");
        List<String> channels = new ArrayList<String>();
        for(TContact contact : user.self_contacts){
            channels.add(Redis.personChannel(contact.person.id));
        }
        StreamComet client = new StreamComet(cometJS,user,channels);
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
            Receiver receiver = new Receiver();
            for(TContact contact : user.self_contacts){
                Redis.subscribe(receiver, Redis.personChannel(contact.person.id));
            }
            }
        });
        receiveThread.start();
        return ok(client);
    }

}
