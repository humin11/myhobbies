package controllers;

import models.*;
import play.mvc.Controller;

import java.util.List;

public class Notifications extends Controller {

    public static void notify(TPost post,List<TContact> followers){
        for(TContact follower : followers){
            notify(post, follower.owner);
        }
    }

    public static void notify(TPost post,User follower){
        TNotification notification = new TNotification();
        notification.recipient = follower;
        notification.source_id = post.id;
        notification.source_type = "POST";
        notification.create_at = post.create_at;
        notification.update_at = post.create_at;
        notification.unread = true;
        notification.save();
    }

    public static void notify(TComment comment){
        TNotification notification = new TNotification();
        notification.recipient = comment.post.author;
        notification.source_id = comment.id;
        notification.source_type = "COMMENT";
        notification.create_at = comment.create_at;
        notification.update_at = comment.create_at;
        notification.unread = true;
        notification.save();
    }

}
