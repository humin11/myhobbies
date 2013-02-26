package controllers;

import models.TUser;
import play.mvc.Controller;
import play.mvc.Result;

public class Stream extends Controller {

    public static Result list(){
        TUser user = TUser.find.byId(Long.valueOf(session("userid")));

        return ok();
    }

}
