package models;

import java.util.{Date}
import play.api.Play.current
import com.novus.salat._
import com.novus.salat.dao._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.novus.salat.global._

case class Court(
  @Key("_id") id: ObjectId = new ObjectId, 
  val name: String, 
  val logo: Option[String] = None, 
  val address: Option[String] = None, 
  val telephone: Option[String] = None, 
  val businesshours: Option[String] = None, 
  val businfo: Option[String] = None,
  val create_at: Option[Date] = None,
  val update_at: Option[Date] = None
)

object Court extends ModelCompanion[Court, ObjectId]{
  val collection = mongoCollection("courts")
  val dao = new SalatDAO[Court, ObjectId](collection = collection) {}

  def findById(id: ObjectId) = dao.findOneById(id)
}
