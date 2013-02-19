package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.data.validation.*;
import play.data.format.*;

@Entity
@Table(name="user")
public class TUser extends Model {
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Email
	public String email;
	
	@Constraints.Required
	public String username;
	
	@Constraints.Required
	public String password;
	
	public String permission;
	
	public Boolean isinit;
	
	public Boolean status;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date create_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date update_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date locked_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date unlocked_at;
	
	public static Finder<Long, TUser> find = new Finder<Long, TUser>(Long.class, TUser.class);
	
	
}

