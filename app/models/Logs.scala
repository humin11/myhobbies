package models

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

case class Logs(
	id: ObjectId = new ObjectId,
	action: String,
	content: String,
	user: User,
 	create_at: Option[Date] = None,
 	update_at: Option[Date] = None
)

object Logs extends ModelCompanion[Logs, ObjectId]{
  val collection = mongoCollection("logs")
  val dao = new SalatDAO[Logs, ObjectId](collection = collection) {}

}
