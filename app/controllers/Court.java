package controllers;

import play.mvc.*;

import views.html.court.*;

public class Court extends Controller {

	public static Result index(){
		return ok(index.render(""));
	}
}
