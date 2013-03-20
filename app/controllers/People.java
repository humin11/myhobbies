package controllers;

import models.*;
import org.codehaus.jackson.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.post.Redis;

import java.util.Date;
import java.util.List;

public class People extends Controller {

    public static Result follow(){
        Date now = new Date();
        String follow_person_id = request().getQueryString("follow_person_id");
        String circle_id = request().getQueryString("circle_id");
        User connectedUser = Application.getLocalUser(session());
        User person = User.find.byId(Long.parseLong(follow_person_id));
        TContact contact = TContact.add(connectedUser,person,now);
        TCircle.addContactById(Long.parseLong(circle_id),contact,now);
        return ok("successed");
    }

    public static Result unfollow(){
        User connectedUser = Application.getLocalUser(session());
        String follow_person_id = request().getQueryString("follow_person_id");
        User person = User.find.byId(Long.parseLong(follow_person_id));
        TContact contact = TContact.findContact(connectedUser,person);
        contact.delete();
        return ok("successed");
    }

    public static Result buildPost(){
        String content = request().getQueryString("content");
        Date now = new Date();
        User connectedUser = Application.getLocalUser(session());
        TPost post = new TPost();
        post.author = connectedUser;
        post.content = content;
        post.create_at = now;
        post.update_at = now;
        post.ispublic = true;
        post.save();
        JsonNode json = Json.toJson(post);
        if(post.ispublic){
            Redis.publish2Person(connectedUser.id, json);
            shareAll(post,connectedUser.in_others_contacts);
            shareSelf(post);
            notifyAll(post,connectedUser.in_others_contacts);
        }else{
            for (TParticipation participation : post.participations){
                if("CIRCLE".equals(participation.type)){
                    shareWith(post,participation.circle);
                }else if("PERSON".equals(participation.type)){
                    shareWith(post,participation.person);
                }
            }
        }
        return ok("ok");
    }

    protected static void shareAll(TPost post,List<TContact> followers){
        for(TContact follower : followers){
            shareWith(post,follower.owner);
        }
    }

    protected static void shareSelf(TPost post){
        shareWith(post,post.author);
    }

    protected static void notifyAll(TPost post,List<TContact> followers){
        for(TContact follower : followers){
            notifyWith(post,follower.owner);
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

    protected static void shareWith(TPost post,User follower){
        TShareVisibility shareVisibility = new TShareVisibility();
        shareVisibility.recipient = follower;
        shareVisibility.post = post;
        shareVisibility.shareable_type = "POST";
        shareVisibility.create_at = post.create_at;
        shareVisibility.update_at = post.create_at;
        shareVisibility.hidden = false;
        shareVisibility.save();
    }

    protected static void notifyWith(TPost post,User follower){
        TNotification notification = new TNotification();
        notification.recipient = follower;
        notification.source_id = post.id;
        notification.source_type = "POST";
        notification.create_at = post.create_at;
        notification.update_at = post.create_at;
        notification.unread = true;
        notification.save();
    }


}
