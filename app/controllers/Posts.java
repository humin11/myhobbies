package controllers;

import models.*;
import org.codehaus.jackson.JsonNode;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

public class Posts extends Controller {

    public static void create(){
        Date now = new Date();
        TUser author = TUser.find.byId(Long.valueOf(session("userid")));
        TPost post = new TPost();
        post.author = author;
        post.create_at = now;
        post.update_at = now;

        post.save();
        JsonNode params = request().body().asJson();

        if(post.ispublic){
            List<TContact> contacts = TContact.findContacts(author);
            shareAll(post,contacts,now);
            notifyAll(post,contacts,now);
        }
    }

    protected static void shareAll(TPost post,List<TContact> contacts,Date now){
        for(TContact contact : contacts){
            TUser recipient = contact.owner;
            shareWith(post,recipient,now);
        }
    }

    protected static void notifyAll(TPost post,List<TContact> contacts,Date now){
        for(TContact contact : contacts){
            TUser recipient = contact.owner;
            notifyWith(post,recipient,now);
        }
    }

    protected static void shareWith(TPost post,TUser recipient,Date now){
        TShareVisibility shareVisibility = new TShareVisibility();
        shareVisibility.recipient = recipient;
        shareVisibility.shareable_id = post.id;
        shareVisibility.shareable_type = "POST";
        shareVisibility.create_at = now;
        shareVisibility.update_at = now;
        shareVisibility.hidden = false;
        shareVisibility.save();
    }

    protected static void notifyWith(TPost post,TUser recipient,Date now){
        TNotification notification = new TNotification();
        notification.recipient = recipient;
        notification.source_id = post.id;
        notification.source_type = "POST";
        notification.create_at = now;
        notification.update_at = now;
        notification.unread = true;
        notification.save();
    }

}
