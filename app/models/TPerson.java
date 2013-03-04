package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="people")
public class TPerson extends Model {

    @Id
    @GeneratedValue
    public Long id;

    public String nickname;

    public String summary;

    public String location;

    public String gender;

    public String qq;

    public Float height;

    public Float weight;

    @OneToOne
    public User user;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

}