package controllers;

import play.mvc.*;
import views.html.*;

public class Application extends Controller {
  
  public static Result index(){
      session("userid","1");
      return ok(index.render("My Applications"));
  }
  
}