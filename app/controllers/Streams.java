package controllers;

import models.TPost;
import models.User;
import org.codehaus.jackson.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Streams extends Controller {

    public static Result list(){
        int pageNum = request().getQueryString("pageNum")==null?1:Integer.parseInt(request().getQueryString("pageNum"));
        int maxRow = request().getQueryString("maxRow")==null?15:Integer.parseInt(request().getQueryString("maxRow"));
        User connectedUser = User.find.byId(Long.valueOf(session("userid")));
        List<TPost> posts = TPost.findSharedPosts(connectedUser,pageNum,maxRow);
        JsonNode result = Json.toJson(posts);
        return ok(result);
    }

}
