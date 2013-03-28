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
  alias: Option[String] = None,
  description: Option[String] = None,
  logo: Option[String] = None,
  address: Option[String] = None,
  telephone: Option[String] = None,
  opentime: Option[String] = None,
  closetime: Option[String] = None,
  businfo: Option[String] = None,
  price: Seq[Price] = Seq.empty,
  tags: Seq[Tag] = Seq.empty,
  create_at: Option[Date] = None,
  update_at: Option[Date] = None
)

case class Price(
  price: Option[Int],
  type_name: Option[String]
)

case class Park(
  is_free: Boolean,
  name: Option[String] = None,
  price: Seq[Price] = Seq.empty
)

object Court extends ModelCompanion[Court, ObjectId]{
  val collection = mongoCollection("courts")
  val dao = new SalatDAO[Court, ObjectId](collection = collection) {}

  collection.ensureIndex(DBObject("name" -> 1), "court_name", unique = true)

  def findById(id: ObjectId) = dao.findOneById(id)

  val columns = List("dummy", "_id", "name", "address", "telephone", "businesshours")

  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = ""): Page[(Court)] = {

    val where = if(filter == "") MongoDBObject.empty else MongoDBObject("name" ->(""".*"""+filter+""".*""").r)
    val ascDesc = if(orderBy > 0) 1 else -1
    val order = MongoDBObject(columns(orderBy.abs) -> ascDesc)

    val totalRows = count(where);
    val offset = pageSize * page
    val courts = find(where).sort(order).limit(pageSize).skip(offset).toSeq

    Page(courts, page, offset, totalRows)
  }
}

/**
 * Helper for pagination.
 */
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}
