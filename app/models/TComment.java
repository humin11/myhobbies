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

    @OneToMany(mappedBy = "like_to")
    @Where(clause = "type='COMMENT'")
    public Set<TLove> likes;

    @ManyToOne
    public Commentable comment_to;

    //e.g. POST,COMMENT,PHOTO
    public String type;

    public static Finder<Long,TComment> find = new Finder<Long, TComment>(Long.class,TComment.class);

}
