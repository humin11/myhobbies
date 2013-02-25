package models;

import com.avaje.ebean.annotation.Where;
import models.base.Commentable;
import models.base.Loveable;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="post")
public class TPost extends Model implements Commentable,Loveable {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String content;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date modify_at;

    @OneToOne
    public TPost parent;

    public String status;

    public Boolean shareable;

    public Boolean commentable;

    public Boolean likeable;

    @OneToMany(mappedBy = "post")
    public List<TPostShare> shares;

    @OneToMany(mappedBy = "post")
    public List<TComment> comments;

    @OneToMany(mappedBy = "parent")
    public List<TPost> reshares;

    @OneToMany(mappedBy = "post")
    public List<TLove> loves;

    @OneToMany(mappedBy = "post")
    public List<TMention> mentions;

    public List<TPhoto> photos;

    public static Finder<Long,TPost> find = new Finder<Long, TPost>(Long.class,TPost.class);

}
