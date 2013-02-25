package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="aspect_members")
public class TAspectMember extends Model {

    @ManyToOne
    public TAspect aspect;

    @OneToOne
    public TContact contact;

    public static Finder<Long,TAspectMember> find = new Finder<Long, TAspectMember>(Long.class,TAspectMember.class);

}
