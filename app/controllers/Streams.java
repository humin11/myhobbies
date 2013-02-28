package controllers;

import models.TPost;
import models.TUser;
import org.codehaus.jackson.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Streams extends Controller {

    public static Result list(){
        JsonNode params = request().body().asJson();
        int pageNum = params.get("pageNum")==null?1:params.get("pageNum").asInt();
        int maxRow = params.get("maxRow")==null?15:params.get("maxRow").asInt();
        TUser connectedUser = TUser.find.byId(Long.valueOf(session("userid")));
        List<TPost> posts = TPost.findSharedPosts(connectedUser,pageNum,maxRow);
        JsonNode result = Json.toJson(posts);
        return ok(result);
    }

}
