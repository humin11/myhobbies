package models;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
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

    @OneToOne
    public TPerson author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date modify_at;

    //e.g. modified or not
    public String type;

    public Long likes;

    @ManyToOne
    public TPost post;

    public static Finder<Long,TComment> find = new Finder<Long, TComment>(Long.class,TComment.class);

}
