package controllers;

import models.TPost;
import models.TUser;
import org.codehaus.jackson.node.ObjectNode;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

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

}
