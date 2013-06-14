import com.mongodb.casbah.Imports._
import play.api._
import libs.ws.WS
import models._
import se.radley.plugin.salat._
import securesocial.core.providers.UsernamePasswordProvider
import securesocial.core._
import play.api.cache.Cache
import play.api.Play.current

object Global extends GlobalSettings{

  override def onStart(app: Application) {

  }

}
