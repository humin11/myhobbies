package models;

import play.db.ebean.Model;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

public class TGroupMember extends Model {

    @ManyToOne
    public TGroup group;

    @OneToOne
    public TUser user;

    public String remark;

    public static Finder<Long,TGroupMember> find = new Finder<Long, TGroupMember>(Long.class,TGroupMember.class);

}
