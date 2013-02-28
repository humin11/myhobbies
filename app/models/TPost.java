package models;

import com.avaje.ebean.annotation.Where;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="posts")
public class TPost extends Model {

    @Id
    @GeneratedValue
    public Long id;

    public String content;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    @ManyToOne
    public TPost parent;

    public String status;

    public Boolean ispublic;

    @OneToMany(mappedBy = "post")
    public List<TComment> comments;

    @OneToMany(mappedBy = "parent")
    public List<TPost> reshares;

    @OneToMany(mappedBy = "post")
    public List<TLike> likes;

    @OneToMany(mappedBy = "post")
    public List<TMention> mentions;

    @OneToMany(mappedBy = "post")
    public List<TPhoto> photos;

    @OneToMany(mappedBy = "post")
    public List<TShareVisibility> shares;

    public static Finder<Long,TPost> find = new Finder<Long, TPost>(Long.class,TPost.class);

    public static List<TPost> findSharedPosts(TUser user,Integer pageNum,Integer maxRow){
        return findSharedPostsFromOthers(user,pageNum,maxRow);
    }

    public static List<TPost> findSharedPostsFromSelf(TUser user,Integer pageNum,Integer maxRow){
        return find.where()
                .eq("author",user)
                .orderBy("create_at DESC")
                .findPagingList(maxRow).getPage(pageNum).getList();
    }

    public static List<TPost> findSharedPostsFromOthers(TUser user,Integer pageNum,Integer maxRow){
        List<TUser> contactUsers = TUser.findContactUsers(user);
        return find.where()
                .in("author",contactUsers)
                .eq("shares.recipient",user.id)
                .eq("shares.hidden",false)
                .orderBy("create_at DESC")
                .findPagingList(maxRow).getPage(pageNum).getList();
    }

}
