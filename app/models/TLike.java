package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="like")
public class TLike extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TPost post;

    @OneToOne
    public TPerson author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;


}
