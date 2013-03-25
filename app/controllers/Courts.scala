package controllers;

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import com.mongodb.casbah.Imports._

import java.util.{Date}

import models._
import views._
import views.html.court._

object Courts extends Controller {

	 val Home = Redirect(routes.Courts.list(0, 2, ""))

	def courtForm(id: ObjectId = new ObjectId) = Form(
		mapping(
			"id" -> ignored(id),
			"name" -> nonEmptyText,
			"logo" -> optional(text),
			"address" -> optional(text),
			"telephone" -> optional(text),
			"businesshours" -> optional(text),
			"businfo" -> optional(text),
			"create_at" -> optional(date("yyyy-MM-dd HH:mm:ss")),
			"update_at" -> optional(date("yyyy-MM-dd HH:mm:ss"))
		)(Court.apply)(Court.unapply)
	)

	def index = Action {
		Home
	}

	def list(page: Int, orderBy: Int, filter: String) = Action { implicit request =>
	    Ok(html.court.index(
	      Court.list(page = page, orderBy = orderBy, filter = filter),
	      orderBy, filter
	    ))
	}
	
	def view(id: ObjectId) = Action {
		Court.findById(id).map(court => 
			Ok(html.court.show(court))
		).getOrElse(NotFound)
	}
	
	def blank = Action {
		/*
		 * court list
		 */
		Ok(html.court.blank(courtForm()))
	}
	
	def save = Action { implicit request => 
		courtForm().bindFromRequest.fold (
		    formWithErrors => BadRequest(html.court.blank(formWithErrors)),
		    {
		    	court => {
		    		Court.insert(court)
		    		Ok(html.court.show(court))
		    	} 
		    }
		)	
	}
	
	def upload = TODO
	
}
