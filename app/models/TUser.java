package models;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import models.base.Shareable;
import play.db.ebean.Model;
import play.data.validation.*;
import play.data.format.*;

@Entity
@Table(name="user")
public class TUser extends Model implements Shareable{
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Email
	public String email;
	
	@Constraints.Required
	public String username;
	
	@Constraints.Required
	public String password;
	
	public String permission;
	
	public Boolean isinit;
	
	public Boolean status;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date create_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date update_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date locked_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date unlocked_at;

    @OneToOne
    public TPerson person;

    @OneToMany(mappedBy = "author")
    public List<TAspect> aspects;

    @OneToMany(mappedBy = "owner")
    public List<TContact> contacts;

    @OneToMany(mappedBy = "person")
    public List<TPostShare> shares;

    @OneToMany(mappedBy = "author")
    public List<TPost> posts;

    @OneToMany(mappedBy = "author")
    public List<TComment> comments;

    @OneToMany(mappedBy = "author")
    public List<TPhoto> photos;

	public static Finder<Long, TUser> find = new Finder<Long, TUser>(Long.class, TUser.class);

    public static TUser findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public static TUser authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public static void sharePost(List<Shareable> shareList,TPost post,TUser user){
        Date now = new Date();
        for(Shareable shareable : shareList){
            TPostShare share = new TPostShare();
            share.author = user;
            share.create_at = now;
            share.post = post;
            if(shareable instanceof TUser){
                share.person = (TUser)shareable;
                share.type = "PERSON";
            }else if(shareable instanceof TAspect){
                share.aspect = (TAspect)shareable;
                share.type = "ASPECT";
            }
            share.save();
        }
        TPostShare share = new TPostShare(); //add self share record
        share.author = user;
        share.create_at = now;
        share.post = post;
        share.person = user;
        share.type = "PERSON";
        share.save();
    }

    public static List<TPost> findPosts(TUser user){
        return TPost.find.where()
                .eq("shares.share_person",user.id)
                .eq("shares.share_aspect.members.contact.member",user.id).orderBy("create_at DESC").findList();
    }

}

