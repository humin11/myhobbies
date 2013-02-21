package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

public class TGroup extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public TUser owner;

    @Constraints.Required
    public String name;

    //e.g. Friend,Custom,Others
    public String type;

    @OneToMany(mappedBy = "group")
    public Set<TGroupMember> members;
}
