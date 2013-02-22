package models;

import models.base.Shareable;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="share")
public class TShare extends Model {

    @ManyToOne
    public TPost post;

    //e.g. USER,ASPECT,COMMUNITIES
    public String type;

    @ManyToOne
    public Shareable share_to;

    @OneToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    public static Finder<Long, TShare> find = new Finder<Long, TShare>(Long.class, TShare.class);

}
