package models;

import play.db.ebean.Model;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class TPostMember extends Model {

    @ManyToOne
    public TPost post;

    //e.g. Person,Group,Communities
    public String type;

    @OneToOne
    public TGroup group;

    @OneToOne
    public TUser user;

}
