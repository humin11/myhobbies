package models;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="comment")
public class TComment extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String content;

    public Long creator_id;

    public String creator;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date modify_at;

    public String reply_to;

    public Long owner;

    public static Finder<Long,TComment> find = new Finder<Long, TComment>(Long.class,TComment.class);

    public static List<TComment> findByOwner(Long parent){
        return find.where().eq("owner",parent).findList();
    }

}
