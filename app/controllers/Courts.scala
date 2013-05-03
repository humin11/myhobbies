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
import indexing.{IndexCourtsManager, IndexCourts}
import org.elasticsearch.index.query.QueryBuilders
import com.github.cleverage.elasticsearch.ScalaHelpers._
import play.api.libs.concurrent.Execution.Implicits._
import com.github.cleverage.elasticsearch.ScalaHelpers.IndexQuery
import play.api.libs.iteratee.Enumerator

object Courts extends Controller {

	 val Home = Redirect(routes.Courts.list(0, 2, ""))

	def courtForm(logo: Option[ObjectId] = null) = Form(
		mapping(
			"name" -> nonEmptyText,
			"logo" -> ignored(logo),
			"address" -> optional(text),
			"telephone" -> optional(text),
			"businfo" -> optional(text),
			"description" -> optional(text),
			"alias" -> optional(text)
		)((name, logo, address, telephone, businfo, description, alias) => Court(name = name, logo = logo, address = address, telephone = telephone, businfo = businfo, description = description, alias = alias))
      	((court) => Some(court.name, court.logo, court.address, court.telephone, court.businfo, court.description, court.alias))
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
	
	def save = Action(parse.multipartFormData) { implicit request => 

		var fileId:Option[ObjectId] = null
		request.body.file("logo").map { picture =>
			import java.io.File
			val filename = picture.filename 
			val contentType = picture.contentType
			fileId = GridFS.save(picture.ref.file, filename, contentType)
		}

		courtForm(fileId).bindFromRequest.fold (
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
