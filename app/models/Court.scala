package models;

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._
import mongoContext._

case class Court(
  @Key("_id") id: ObjectId = new ObjectId, 
  name: String,
  logo: Option[String] = None,
  address: Option[String] = None,
  telephone: Option[String] = None,
  businesshours: Option[String] = None,
  businfo: Option[String] = None,
  create_at: Option[Date] = None,
  update_at: Option[Date] = None
)

object Court extends ModelCompanion[Court, ObjectId]{
  val collection = mongoCollection("courts")
  val dao = new SalatDAO[Court, ObjectId](collection = collection) {}

  def findById(id: ObjectId) = dao.findOneById(id)

}
