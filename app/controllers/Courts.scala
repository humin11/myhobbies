package controllers;

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import java.util.{Date}

import anorm._

import models._
import views._
import views.html.court._

object Courts extends Controller {

	val courtForm: Form[Court] = Form(
		mapping(
			"name" -> nonEmptyText,
			"logo" -> text,
			"address" -> nonEmptyText,
			"telephone" -> text,
			"businesshours" -> text,
			"businfo" -> text
		)(Court.apply)(Court.unapply)
	)

	def index = Action {
		/*
		 * court list
		 */
		Ok(html.court.index(null))
	}
	
	def view(id: Long) = Action {
		/*
		 * court list
		 */
		Ok(html.court.show(null))
	}
	
	def blank = Action {
		/*
		 * court list
		 */
		Ok(html.court.blank(courtForm))
	}
	
	def save = Action {
		/*
		 * court list
		 */
		Ok(html.court.index(null))
	}
	
	def upload = TODO
	
}
