package models;

import play.db.ebean.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
    public TUser user;

}