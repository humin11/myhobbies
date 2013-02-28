package controllers;

import models.TCircle;
import models.TContact;
import models.TUser;
import org.codehaus.jackson.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;

public class People extends Controller {

    public static Result follow(){
        Date now = new Date();
        JsonNode params = request().body().asJson();
        TUser connectedUser = TUser.find.byId(Long.valueOf(session("userid")));
        TUser person = TUser.find.byId(params.get("follow_person_id").asLong());
        TContact contact = TContact.add(connectedUser,person,now);
        TCircle.addContactById(params.get("circle_id").asLong(),contact,now);
        return ok("successed");
    }

    public static Result unfollow(){
        JsonNode params = request().body().asJson();
        TUser connectedUser = TUser.find.byId(Long.valueOf(session("userid")));
        TUser person = TUser.find.byId(params.get("follow_person_id").asLong());
        TContact contact = TContact.findContact(connectedUser,person);
        contact.delete();
        return ok("successed");
    }

}
