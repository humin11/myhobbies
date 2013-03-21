package controllers;

import play.api.mvc._
import securesocial.core.{Identity, Authorization}
import play.Logger

object Application extends Controller {

	def index = Action { implicit request =>
    Ok(views.html.index(""))
  }
}

case class WithProvider(provider: String) extends Authorization {
	  def isAuthorized(user: Identity) = {
	    user.id.providerId == provider
	  }
}