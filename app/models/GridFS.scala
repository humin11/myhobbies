package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.io.File

import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._
import mongoContext._

object GridFS {
	val files = gridFS("gridfiles")

	def save(file: File, filename: String, contentType: Option[String]) = {
		val gridFile = files.createFile(file)
		gridFile.filename = filename
		gridFile.contentType = contentType.orNull
		gridFile.save()
		gridFile._id
	}

	def get(objId: Option[ObjectId]) =  objId match {
		case Some(id) => files.findOne(id)
		case None => None
	}

	def delete(objId: ObjectId) = {
		files.remove(objId)
	}
}