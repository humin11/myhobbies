package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="group")
public class TGroup extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public TPerson owner;

    @Constraints.Required
    public String name;

    //e.g. Friend,Custom,Others
    public String type;

    @OneToMany(mappedBy = "group")
    public Set<TGroupMember> members;
}
