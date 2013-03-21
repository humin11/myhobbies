package controllers;

import models.*;
import org.codehaus.jackson.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.post.Redis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

public class Posts extends Controller {

    public static Result create(){
        String content = request().getQueryString("content");
        Date now = new Date();
        User localUser = Application.getLocalUser(session());
        TPost post = new TPost();
        post.author = localUser;
        post.content = content;
        post.create_at = now;
        post.update_at = now;
        post.ispublic = true;
        post.save();
        JsonNode json = Json.toJson(post);
        if(post.ispublic){
            Redis.publish2Person(localUser.id, json);
            shareAll(post,localUser.in_others_contacts);
            shareSelf(post);
            Notifications.notify(post, localUser.in_others_contacts);
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

}
