package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
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
    public List<TShareVisibility> share_visibilities;

    @OneToMany(mappedBy = "post")
    public List<TCircleVisibility> circle_visibilities;

    @OneToMany(mappedBy = "post")
    public List<TParticipation> participations;

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
        String contactUserIds = "";
        for (TUser contactUser : contactUsers){
            contactUserIds += contactUser.id;
            contactUserIds += ",";
        }
        if(contactUserIds.endsWith(","))
            contactUserIds = contactUserIds.substring(0,contactUserIds.length()-2);
        String sql = "select a.* from posts a,share_visibilities b,circle_visibilities c,circle_members d" +
                "where a.author_id in (:contactUserIds) and a.id = b.post_id and b.recipient = :recipient and b.hidden = 0 " +
                "or a.id = c.post_id and c.circle_id = d.circle_id and d.person= :recipient";
        RawSql rawSql = RawSqlBuilder.parse(sql).create();
        return find.setRawSql(rawSql)
                .setParameter("contactUserIds",contactUserIds)
                .setParameter("recipient",user.id)
                .orderBy("create_at DESC")
                .setDistinct(true)
                .findPagingList(maxRow).getPage(pageNum).getList();
        /*return find.where()
                .or(
                    Expr.and(
                            Expr.in("author",contactUsers),
                            Expr.and(
                                    Expr.eq("share_visibilities.recipient",user.id),
                                    Expr.eq("share_visibilities.hidden",false))),
                    Expr.eq("circle_visibilities.circle.circle_members.person",user.id)
                )
                .orderBy("create_at DESC")
                .setDistinct(true)
                .findPagingList(maxRow).getPage(pageNum).getList();*/
    }

}
