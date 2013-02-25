package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name="mention")
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
