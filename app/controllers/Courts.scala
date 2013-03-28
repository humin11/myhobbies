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

object Courts extends Controller {

	 val Home = Redirect(routes.Courts.list(0, 2, ""))

	def courtForm() = Form(
		mapping(
			"name" -> nonEmptyText,
			"logo" -> optional(text),
			"address" -> optional(text),
			"telephone" -> optional(text),
			"businfo" -> optional(text)
		)((name, logo, address, telephone, businfo) => Court(name = name, logo = logo, address = address, telephone = telephone, businfo = businfo))
      	((court) => Some(court.name, court.logo, court.address, court.telephone, court.businfo))
	)

	def index = Action {
		val indexQuery = IndexQuery[IndexCourts]().withBuilder(QueryBuilders.matchQuery("name", "dF"))
    	val results: IndexResults[IndexCourts] = IndexCourtsManager.search(indexQuery)
    	println(results.totalCount)
		Ok
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
