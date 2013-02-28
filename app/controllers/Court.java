package controllers;

import java.util.List;

import models.TCourt;
import play.mvc.*;

import views.html.court.*;

public class Court extends Controller {

	public static Result index(){
		List<TCourt> courtList = TCourt.findByCity(1);
		return ok(index.render(courtList));
	}
	
	public static Result view(Long id){
		TCourt court = TCourt.findById(id);
		return ok(show.render(court));
	}
	
}
