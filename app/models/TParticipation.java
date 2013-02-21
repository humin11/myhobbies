package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="participation")
public class TParticipation extends Model {

    @ManyToOne
    public TPost post;

    //e.g. Person,Group,Communities
    public String type;

    @OneToOne
    public TGroup group;

    @OneToOne
    public TPerson person;

    @OneToOne
    public TPerson author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

}
