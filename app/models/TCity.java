package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="s_global_region")
public class TCity extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public int region_id;

	public int parent_id;

	public String region_name;
	
	public int region_type;

	public static Finder<Long, TCity> find = new Finder<Long, TCity>(Long.class, TCity.class);
	
	public static TCity findByName(String name){
		return find.where().eq("region_name", name).findUnique();	
	}
}