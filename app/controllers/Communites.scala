package controllers;

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._
import views._
import views.html.communites._

object Communites extends Controller {

	def index = Action {
		Ok(html.communites.index())
	}

}