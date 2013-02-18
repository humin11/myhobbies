package controllers;

import models.TUser;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
  
  public static Result index(){
	  TUser.find.all();
	  return ok(index.render("My Applications"));
  }
  
}