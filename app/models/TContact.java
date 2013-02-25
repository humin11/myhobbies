package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="contact")
public class TContact extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public TUser owner;

    @OneToOne
    public TUser member;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;
}
