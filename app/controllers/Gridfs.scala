package controllers;

import _root_.models._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import com.mongodb.casbah.Imports._
import java.util.{Date}
import models._
import views._
import views.html.court._
import play.api.libs.iteratee.Enumerator
import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._

object Gridfs extends Controller {

	def getLogo(file: Option[ObjectId]) = Action {
		GridFS.get(file) match {
		case Some(f) => SimpleResult(
				ResponseHeader(OK, Map(
				CONTENT_LENGTH -> f.length.toString,
				CONTENT_TYPE -> f.contentType.getOrElse(BINARY))),
			Enumerator.fromStream(f.inputStream)
		)
		case None => NotFound
		}
	}
	
}