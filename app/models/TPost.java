package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
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
    public TPerson author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date modify_at;

    @OneToOne
    public TPost parent;

    //e.g. Public,Friend,FriendRelation,CustomGroup
    public String status;

    public Boolean shareable;

    public Boolean commentable;

    public Boolean likeable;

    @OneToMany(mappedBy = "post")
    public Set<TParticipation> participations;

    @OneToMany(mappedBy = "post")
    public Set<TComment> comments;

    @OneToMany(mappedBy = "parent")
    public Set<TPost> reshares;

    public Set<TLike> likes;

    public Set<TPhoto> photos;

    public static Finder<Long,TPost> find = new Finder<Long, TPost>(Long.class,TPost.class);

}
