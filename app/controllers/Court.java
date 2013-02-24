package controllers;

import models.TCourt;
import play.mvc.*;

import views.html.court.*;

public class Court extends Controller {

	public static Result index(){
		return ok(index.render(""));
	}
	
	public static Result view(Long id){
		TCourt court = TCourt.findById(id);
		return ok(show.render(court));
	}
}
