package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="notifications")
public class TNotification extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    @ManyToOne
    public TUser recipient;

    public Long source_id;

    //e.g. POST,COMMENT
    public String source_type;

    public Boolean unread;

}
