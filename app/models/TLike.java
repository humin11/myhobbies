package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="likes")
public class TLike extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TPost post;

    @ManyToOne
    public TComment comment;

    //e.g. POST,COMMENT,PHOTO
    public String type;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;


}
