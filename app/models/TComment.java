package models;

import models.base.Commentable;
import models.base.Loveable;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="comment")
public class TComment extends Model implements Commentable,Loveable {

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

    //e.g. modified or not
    public String status;

    @ManyToOne
    public TPost post;

    @OneToMany(mappedBy = "comment")
    public List<TLove> loves;

    @OneToMany(mappedBy = "comment")
    public List<TMention> mentions;

    //e.g. POST,COMMENT,PHOTO
    public String type;

    public static Finder<Long,TComment> find = new Finder<Long, TComment>(Long.class,TComment.class);

}
