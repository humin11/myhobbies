package controllers;

import models.*;
import org.codehaus.jackson.node.ObjectNode;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

public class Posts extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result list(){
        ObjectNode result = Json.newObject();
        TUser user = TUser.find.byId(Long.valueOf(session("userid")));
        List<TPost> posts = TPost.findPosts(user);
        Cache.set("", "");
        return ok(result);
    }

    public static Result create(){
        Date now = new Date();
        TUser author = TUser.find.byId(Long.valueOf(session("userid")));
        TPost post = new TPost();
        post.author = author;
        post.create_at = now;
        post.update_at = now;

        post.save();

        if(post.ispublic){
            List<TContact> contacts = TContact.find.where().eq("member",author.id).findList();
            shareAll(post,contacts,now);
            notifyAll(post,contacts,now);
        }else{

        }
        return ok();
    }

    protected static void shareAll(TPost post,List<TContact> contacts,Date now){
        for(TContact contact : contacts){
            TUser recipient = contact.owner;
            TShareVisibility shareVisibility = new TShareVisibility();
            shareVisibility.recipient = recipient;
            shareVisibility.shareable_id = post.id;
            shareVisibility.shareable_type = "POST";
            shareVisibility.create_at = now;
            shareVisibility.update_at = now;
            shareVisibility.hidden = false;
            shareVisibility.save();
        }
    }

    protected static void notifyAll(TPost post,List<TContact> contacts,Date now){
        for(TContact contact : contacts){
            TUser recipient = contact.owner;
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

}
