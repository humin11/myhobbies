package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="post_share")
public class TPostShare extends Model {

    @ManyToOne
    public TPost post;

    @ManyToOne
    public TUser share_person;

    @ManyToOne
    public TAspect share_aspect;

    //e.g. PERSON,GROUP
    public String share_type;

    @OneToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    public static Finder<Long, TPostShare> find = new Finder<Long, TPostShare>(Long.class, TPostShare.class);

}
