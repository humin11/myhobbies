package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name="mentions")
public class TMention extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public TUser user;

    public Long source_id;

    //e.g. POST,COMMENT
    public String source_type;

}
