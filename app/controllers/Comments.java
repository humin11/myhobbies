package controllers;

import models.TComment;
import models.TPost;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;

public class Comments extends Controller {

    public static Result create(){
        Long postId = Long.parseLong(request().getQueryString("postId"));
        TPost post = TPost.find.byId(postId);
        String content = request().getQueryString("content");
        System.out.println(postId+":"+content);
        Date now = new Date();
        User localUser = Application.getLocalUser(session());
        TComment comment = new TComment();
        comment.author = localUser;
        comment.content = content;
        comment.create_at = now;
        comment.update_at = now;
        comment.status = "NEW";
        comment.post = post;
        comment.commentable_type = "POST";
        comment.save();
        if(localUser != post.author)
            Notifications.notify(comment);
        return ok("ok");
    }

}
