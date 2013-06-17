package controllers;

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._
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
import indexing.{IndexCourtsManager, IndexCourts}
import concurrent.Future

object Courts extends Controller with securesocial.core.SecureSocial {

	 val Home = Redirect(routes.Courts.list(0, 2, ""))

	def courtForm(logo: Option[ObjectId] = null) = Form(
		mapping(
			"name" -> nonEmptyText,
			"logo" -> ignored(logo),
			"address" -> optional(text),
			"telephone" -> optional(text),
			"businfo" -> optional(text),
			"description" -> optional(text),
			"longitude" -> optional(text),
			"latitude" -> optional(text),
			"alias" -> optional(text)
		)((name, logo, address, telephone, businfo, description, longitude, latitude, alias) => Court(name = name, logo = logo, address = address, telephone = telephone, businfo = businfo, description = description, longitude=longitude, latitude=latitude, alias = alias))
      	((court) => Some(court.name, court.logo, court.address, court.telephone, court.businfo, court.description, court.longitude, court.latitude, court.alias))
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

  def searchByName(page: Int, orderBy: Int, filter: String) = Action { implicit request =>
      var items = Court.searchByName(page, orderBy, filter).toList
      render {
        case Accepts.Html() => Ok(html.court.blank(courtForm()))
        case Accepts.Json() => Ok(Json.toJson(items))
      }
  }
	
	def view(id: ObjectId) = Action {
		Court.findById(id).map(court => 
			Ok(html.court.show(court))
		).getOrElse(NotFound)
	}
	
	def blank = SecuredAction { implicit request =>
		/*
		 * court list
		 */
		println(request.user)
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
	
	def search = Action {
		//val indexQuery = IndexQuery[IndexCourts]().withBuilder(QueryBuilders.matchQuery("name", "test1234"))

		val indexQuery = IndexCourtsManager.query.withBuilder(QueryBuilders.matchQuery("name", "test1234"))

    	val results: Future[(IndexResults[IndexCourts])] = IndexCourtsManager.searchAsync(indexQuery)

    	 Logger.info("IndexTestManager.search()" + results)
    	Async {
    	results.map { case (r1) =>
        Ok("IndexTestManager.search()" + r1.totalCount)
    }
      }

      	
	}
}
