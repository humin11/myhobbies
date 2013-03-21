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


}
