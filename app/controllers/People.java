package controllers;

import models.TContact;
import models.TUser;
import org.codehaus.jackson.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

public class People extends Controller {

    public static Result follow(){
        JsonNode params = request().body().asJson();
        TUser author = TUser.find.byId(Long.valueOf(session("userid")));
        TContact contact = new TContact();
        return ok();
    }

    public static Result unfollow(){
        JsonNode params = request().body().asJson();
        TUser author = TUser.find.byId(Long.valueOf(session("userid")));
        TUser person = TUser.find.byId(params.get("follow_person_id").asLong());
        TContact contact = TContact.findContact(author,person);
        contact.delete();
        return ok();
    }

}
