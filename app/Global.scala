import com.mongodb.casbah.Imports._
import play.api._
import libs.ws.WS
import models._
import se.radley.plugin.salat._

object Global extends GlobalSettings{

  override def onStart(app: Application) {
    if (User.count(DBObject(), Nil, Nil) == 0) {
      User.save(User(name = "admin"))
    }
  }

}
