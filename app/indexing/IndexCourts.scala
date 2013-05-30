package indexing

import com.github.cleverage.elasticsearch.ScalaHelpers._
import com.mongodb.casbah.Imports._
import java.util.Date


case class IndexCourts(id: String, name: String) extends Indexable

object IndexCourtsManager extends IndexableManager[IndexCourts] {
  import play.api.libs.json._

  val indexType = "nameidx"
  val reads: Reads[IndexCourts] = Json.reads[IndexCourts]
  val writes: Writes[IndexCourts] = Json.writes[IndexCourts]
}