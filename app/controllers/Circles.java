package controllers;

import models.TCircle;
import models.TContact;
import models.TUser;
import org.codehaus.jackson.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;

public class Circles extends Controller {

    public static Result create(){
        Date now = new Date();
        JsonNode params = request().body().asJson();
        TUser connectedUser = TUser.find.byId(Long.valueOf(session("userid")));
        TCircle circle = new TCircle();
        circle.name = params.get("new_circle_name").asText();
        circle.author = connectedUser;
        circle.create_at = now;
        circle.update_at = now;
        circle.save();
        return ok();
    }

    public static Result remove(){
        JsonNode params = request().body().asJson();
        TUser connectedUser = TUser.find.byId(Long.valueOf(session("userid")));
        TCircle circle = TCircle.find.byId(params.get("circle_id").asLong());
        circle.delete();
        TContact.deleteOrphans(connectedUser);
        return ok();
    }


}
