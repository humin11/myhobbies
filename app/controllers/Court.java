package controllers;

import java.util.List;

import models.TCourt;
import play.Logger;
import static play.data.Form.*;
import play.data.*;
import play.mvc.*;

import views.html.court.*;

public class Court extends Controller {

	final static Form<TCourt> courtForm = form(TCourt.class);

	public static Result index(){
		List<TCourt> courtList = TCourt.findByCity(1);
		return ok(index.render(courtList));
	}
	
	public static Result view(Long id){
		TCourt court = TCourt.findById(id);
		return ok(show.render(court));
	}

	public static Result blank(){
		return ok(blank.render(courtForm));
	}
	
	public static Result save(){
		Form<TCourt> filledForm = courtForm.bindFromRequest();
		if(filledForm.hasErrors()){
			return badRequest(blank.render(courtForm));
		} else {
			TCourt court = filledForm.get();
			court.save();
			List<TCourt> courtList = TCourt.findByCity(1);
			return ok(index.render(courtList));
		}
	}

}
