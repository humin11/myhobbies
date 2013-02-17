package models

import play.api.db._
import anorm._
import anorm.SqlParser._
import play.api.Play.current
import org.mindrot.jbcrypt.BCrypt

case class TUser(id: String, email: String, username: String, password: String, permission: String, isinit: Int, status: Int)

object TUser {
  
  val simple = {
    get[String]("user.id") ~
    get[String]("user.email") ~
    get[String]("user.username") ~
    get[String]("user.password") ~
    get[String]("user.permission") ~
    get[Int]("user.isinit") ~
    get[Int]("user.status") map {
      case id~email~username~password~permission~isinit~status => TUser(id,email,username,password,permission,isinit,status)
    }
  }
  
  def findByEmail(email: String): Option[TUser] = {
    DB.withConnection { implicit connection => 
      SQL("select * from user where email= {email} ").on('email->email).as(simple.singleOpt)
    }
  }
  
  def findByUsername(username: String): Option[TUser] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where username = {username}").on('username->username).as(simple.singleOpt);
    }
  }
  
  def authenticate(email: String, username: String, password: String) : Option[TUser] = {
	findByEmail(email).filter( user => BCrypt.checkpw(password, user.password))
  }
  
  
}