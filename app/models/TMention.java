package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="mention")
public class TMention extends Model {

    @ManyToOne
    public TPost post;

    @ManyToOne
    public TUser user;

}
