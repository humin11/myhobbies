package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="contacts")
public class TContact extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TUser owner;

    @OneToOne
    public TUser person;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    @OneToMany(mappedBy = "contact")
    public List<TCircleMember> circle_members;

    @Transient
    public Boolean sharing;

    public static Finder<Long,TContact> find = new Finder<Long, TContact>(Long.class,TContact.class);

}
