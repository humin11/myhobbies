package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="groups_member")
public class TGroupMember extends Model {

    @ManyToOne
    public TGroup group;

    @OneToOne
    public TUser user;

    public String remark;

    public static Finder<Long,TGroupMember> find = new Finder<Long, TGroupMember>(Long.class,TGroupMember.class);

}
