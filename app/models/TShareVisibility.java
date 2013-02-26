package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="share_visibilities")
public class TShareVisibility extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TContact contact;

    public Long shareable_id;

    //e.g. POST,PHOTO
    public String shareable_type;

    public Boolean hidden;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

}
