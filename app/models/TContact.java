package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="contacts")
public class TContact extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TUser owner;

    @OneToOne
    public TUser member;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    public static Finder<Long,TContact> find = new Finder<Long, TContact>(Long.class,TContact.class);
}
