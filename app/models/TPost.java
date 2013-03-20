package models;

import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
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
    @JsonBackReference
    public User author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    @ManyToOne
    public TPost parent;

    public String status;

    public Boolean ispublic;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    public List<TComment> comments;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    public List<TPost> reshares;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    public List<TLike> likes;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    public List<TMention> mentions;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    public List<TPhoto> photos;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    public List<TShareVisibility> share_visibilities;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    public List<TCircleVisibility> circle_visibilities;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    public List<TParticipation> participations;

    public static Finder<Long,TPost> find = new Finder<Long, TPost>(Long.class,TPost.class);

    public static List<TPost> findSelfPosts(User user,Integer pageNum,Integer maxRow){
        return find.where()
                .eq("author",user)
                .orderBy("create_at DESC")
                .findPagingList(maxRow).getPage(pageNum).getList();
    }

    public static List<TPost> findPublics(User user,Integer pageNum,Integer maxRow){
        return find.where()
                .eq("share_visibilities.recipient",user)
                .eq("share_visibilities.hidden",false)
                .orderBy("create_at DESC")
                .setDistinct(true)
                .findPagingList(maxRow).getPage(pageNum).getList();
    }

    public static List<TPost> findShared(User user,Integer pageNum,Integer maxRow){
        List<TContact> followed = user.self_contacts;
        String followedUserIds = "";
        for (TContact followedUser : followed){
            followedUserIds += followedUser.person;
            followedUserIds += ",";
        }
        if(followedUserIds.endsWith(","))
            followedUserIds = followedUserIds.substring(0,followedUserIds.length()-2);
        String sql = "select a.* from posts a,share_visibilities b,circle_visibilities c,circle_members d" +
                "where a.author_id in (:followedUserIds) and a.id = b.post_id and b.recipient = :recipient and b.hidden = 0 " +
                "or a.id = c.post_id and c.circle_id = d.circle_id and d.person= :recipient";
        RawSql rawSql = RawSqlBuilder.parse(sql).create();
        return find.setRawSql(rawSql)
                .setParameter("followedUserIds",followedUserIds)
                .setParameter("recipient",user.id)
                .orderBy("create_at DESC")
                .setDistinct(true)
                .findPagingList(maxRow).getPage(pageNum).getList();
    }

}
