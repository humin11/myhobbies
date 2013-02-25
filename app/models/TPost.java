package models;

import com.avaje.ebean.annotation.Where;
import models.base.Commentable;
import models.base.Loveable;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
    public Set<TPostShare> shares;

    @OneToMany(mappedBy = "comment_to")
    @Where(clause = "type='POST'")
    public Set<TComment> comments;

    @OneToMany(mappedBy = "parent")
    public Set<TPost> reshares;

    @OneToMany(mappedBy = "love_to")
    @Where(clause = "type='POST'")
    public Set<TLove> loves;

    public Set<TPhoto> photos;

    public static Finder<Long,TPost> find = new Finder<Long, TPost>(Long.class,TPost.class);

}
