package models;

import models.base.Shareable;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="aspect")
public class TAspect extends Model implements Shareable{

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Constraints.Required
    public String name;

    @OneToMany(mappedBy = "aspect")
    public List<TAspectMember> members;

    @OneToMany(mappedBy = "aspect")
    public List<TPostShare> shares;

    public static Finder<Long,TAspect> find = new Finder<Long, TAspect>(Long.class,TAspect.class);

}
