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
            shareAll(post,contacts);
            shareSelf(post);
            notifyAll(post,contacts);
        }
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

    protected static void shareWith(TPost post,TUser recipient){
        TShareVisibility shareVisibility = new TShareVisibility();
        shareVisibility.recipient = recipient;
        shareVisibility.post = post;
        shareVisibility.share_type = "POST";
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
