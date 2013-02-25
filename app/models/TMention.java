package models;

import play.db.ebean.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class TMention extends Model {

    @Id
    public GeneratedValue id;

    @OneToOne
    public TUser user;

    @ManyToOne
    public TPost post;

    @ManyToOne
    public TComment comment;

}
