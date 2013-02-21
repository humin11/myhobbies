package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="post")
public class TPost extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String content;

    @OneToOne
    public TUser creator;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date modify_at;

    //e.g. Own,Forward
    public String type;

    //forward from
    @OneToOne
    public TPost owner;

    //e.g. Public,Friend,FriendRelation,CustomGroup
    public String status;

    //allow add comment or not
    public Boolean locked;

    public Boolean hasPhoto;

    public Long likes;

    @OneToMany(mappedBy = "owner")
    public Set<TComment> comments;

    @OneToMany(mappedBy = "post")
    public Set<TPostMember> members;

    public static Finder<Long,TPost> find = new Finder<Long, TPost>(Long.class,TPost.class);

}
