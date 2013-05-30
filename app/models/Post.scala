package models;

import play.api.Play.current
import java.util.Date
import com.novus.salat.annotations._
import se.radley.plugin.salat.Binders._
import org.bson.types.ObjectId
import se.radley.plugin.salat.Binders.ObjectId
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import se.radley.plugin.salat._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import securesocial.core._
import mongoContext._

case class Post(
  id: ObjectId = new ObjectId,
  author: UserId,
  content: String,
  create_at: Date,
  update_at: Date,
  parent: Option[ObjectId] = None,
  is_reshare: Boolean = false
)

object Post extends ModelCompanion[Post, ObjectId]{

  val collection = mongoCollection("posts")
  val dao = new SalatDAO[Post, ObjectId](collection = collection) {}

  def findShares(pageNum:Int = 0,maxRow:Int = 15)(implicit user:User) = {
    ShareVisibility.find(MongoDBObject("recipient" -> user.id, "hidden" -> false))
      .sort(MongoDBObject("create_at" -> -1))
      .skip(pageNum*maxRow)
      .limit(maxRow)
      .map{ shareVisibility =>
        findOneById(shareVisibility.post)
      }
      .filter(_.isDefined).map(_.get).toSeq
  }

  def findParentId(postId:ObjectId):ObjectId = findOne(MongoDBObject("id" -> postId)).get.parent match {
    case p:Some[ObjectId] => p.get
    case None => postId
  }

  def findParent(postId:ObjectId):Option[Post] = findOne(MongoDBObject("id" -> postId)).get.parent match {
    case parentId:Some[ObjectId] => dao.findOneById(parentId.get)
    case None => None
  }

  def reshareCounts(postId:ObjectId) = count(MongoDBObject("parent" -> postId))

}
