package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="group")
public class TGroup extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Constraints.Required
    public String name;

    @OneToMany(mappedBy = "group")
    public Set<TGroupMember> members;
}
