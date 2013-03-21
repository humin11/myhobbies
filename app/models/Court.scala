package models;

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Court(name: String, logo: String, address: String, telephone: String, businesshours: String, businfo: String)

object Court {
  val simple = {
      get[String]("court.name") ~
      get[String]("court.logo") ~
      get[String]("court.address") ~
      get[String]("court.telephone") ~
      get[String]("court.businesshours") ~
      get[String]("court.businfo") map {
        case name ~ logo ~ address ~ telephone ~ businesshours ~ businfo  => Court(name, logo, address, telephone, businesshours, businfo)
      }
  }

}
