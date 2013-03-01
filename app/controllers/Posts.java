package controllers;

import models.*;
import org.codehaus.jackson.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

public class Posts extends Controller {

    public static Result create(){
        System.out.println("*********create*********");
        JsonNode params = request().body().asJson();
        Date now = new Date();
        TUser connectedUser = TUser.find.byId(Long.valueOf(session("userid")));
        TPost post = new TPost();
        post.author = connectedUser;
        post.create_at = now;
        post.update_at = now;
        post.ispublic = true;
        post.save();

        if(post.ispublic){
            List<TContact> contacts = TContact.findContacts(connectedUser);
            shareAll(post,contacts);
            shareSelf(post);
            notifyAll(post,contacts);
        }else{
            for (TParticipation participation : post.participations){
                if("CIRCLE".equals(participation.type)){
                    shareWith(post,participation.circle);
                }else if("PERSON".equals(participation.type)){
                    shareWith(post,participation.person);
                }
            }
        }
        return ok();
    }

    protected static void shareAll(TPost post,List<TContact> contacts){
        for(TContact contact : contacts){
            TUser recipient = contact.owner;
            shareWith(post,recipient);
        }
    }

    protected static void shareSelf(TPost post){
        shareWith(post,post.author);
    }

    protected static void notifyAll(TPost post,List<TContact> contacts){
        for(TContact contact : contacts){
            TUser recipient = contact.owner;
            notifyWith(post,recipient);
        }
    }

    protected static void shareWith(TPost post,TCircle circle){
        TCircleVisibility circleVisibility = new TCircleVisibility();
        circleVisibility.post = post;
        circleVisibility.shareable_type = "POST";
        circleVisibility.create_at = post.create_at;
        circleVisibility.update_at = post.create_at;
        circleVisibility.save();
    }

    protected static void shareWith(TPost post,TUser recipient){
        TShareVisibility shareVisibility = new TShareVisibility();
        shareVisibility.recipient = recipient;
        shareVisibility.post = post;
        shareVisibility.shareable_type = "POST";
        shareVisibility.create_at = post.create_at;
        shareVisibility.update_at = post.create_at;
        shareVisibility.hidden = false;
        shareVisibility.save();
    }

    protected static void notifyWith(TPost post,TUser recipient){
        TNotification notification = new TNotification();
        notification.recipient = recipient;
        notification.source_id = post.id;
        notification.source_type = "POST";
        notification.create_at = post.create_at;
        notification.update_at = post.create_at;
        notification.unread = true;
        notification.save();
    }

}
