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
  	Cache.set("test","123")
    if (User.count(DBObject(), Nil, Nil) == 0) {
      User.save(User(id=UserId("1",UsernamePasswordProvider.UsernamePassword), name = "admin", firstName="admin", lastName="admin", fullName="admin",email=Some("admin@lexiang12.com"), avatarUrl=Some(""), authMethod=AuthenticationMethod.UserPassword))
      //User.save(User(name = "guozhaolong"))
      //User.save(User(name = "test"))
      //User.save(User(name = "hasky"))
    }
  }

}
