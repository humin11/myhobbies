package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.data.validation.*;

@Entity
@Table(name="court")
public class TCourt extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8307669596439812153L;
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String logo;
	
	@Constraints.Required
	public String address;
	
	public String telephone;
	
	public int type;
	
	public String businesshours;
	
	public String businfo;
	
	public String parking;
	
	public String price;
	
	public Double longitude;
	
	public Double latitude;
	
	public static Finder<Long, TCourt> find = new Finder<Long, TCourt>(Long.class, TCourt.class);
	
	public static List<TCourt> findAll(){
		return find.all();
	}
	
	public static TCourt findByName(String name){
		return find.where().eq("name", name).findUnique();
	}
	
	public static void create(TCourt court){
		if(findByName(court.name)==null){
			court.save();
		}
	}
	
	public static TCourt findById(Long id){
		return find.byId(id);
	}

}
