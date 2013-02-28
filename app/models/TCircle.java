package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="circles")
public class TCircle extends Model{

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    @Constraints.Required
    public String name;

    public Integer order_id;

    @OneToMany(mappedBy = "circle")
    public List<TCircleMember> circle_members;

    @OneToMany(mappedBy = "circle")
    public List<TCircleVisibility> circle_visibilities;

    public static Finder<Long,TCircle> find = new Finder<Long, TCircle>(Long.class,TCircle.class);

    public static List<TCircle> findAllCircles(TUser user){
        return find.where().eq("author",user.id).findList();
    }

    public static List<TCircle> findCirclesWithPerson(TUser user,TUser person){
        return find.where()
                .eq("author",user.id)
                .eq("circle_members.person",person.id)
                .findList();
    }

}
