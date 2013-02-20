package models;

import play.db.ebean.Model;

public class TPostMember extends Model {

    public Long postId;

    //e.g. Person,Group,Communities
    public String type;

    public Long groupId;

    public Long userId;

}
