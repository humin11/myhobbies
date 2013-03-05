package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name="profiles")
public class TProfile extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public User user;

    public String avatar_small;

    public String avatar_medium;

    public String avatar_large;

    //e.g. ANYONE,CIRCLES,EXTENDED,SELF,CUSTOM (default EXTENDED)
    public String notification_type;

    //e.g. ANYONE,CIRCLES,EXTENDED,SELF,CUSTOM (default ANYONE)
    public String comment_type;

}
