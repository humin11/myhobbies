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
@Table(name="post")
public class TPost extends Model {

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

    //e.g. Own,Forward
    public String type;

    //forward from
    public Long owner;

    //e.g. Public,Friend,FriendRelation,CustomGroup
    public String status;

    //allow add comment or not
    public Boolean locked;

    public Boolean hasPhoto;

    public static Finder<Long,TPost> find = new Finder<Long, TPost>(Long.class,TPost.class);

    public static List<TPost> findByCreatorId(Long userId){
        return find.where().eq("creatorId",userId).findList();
    }

}
