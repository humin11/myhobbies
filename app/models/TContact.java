package models;

import com.avaje.ebean.Ebean;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="contacts")
public class TContact extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public User owner;

    @OneToOne
    public User person;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    @OneToMany(mappedBy = "contact")
    public List<TCircleMember> circle_members;

    public Boolean sharing = true;

    public Boolean receiving = true;

    public static Finder<Long,TContact> find = new Finder<Long, TContact>(Long.class,TContact.class);

    public static List<TContact> findContacts(User user){
        return find.where().eq("owner",user).findList();
    }

    public static TContact findContact(User user,User person){
        return find.where()
                .eq("owner",user)
                .eq("person",person).findUnique();
    }

    public static TContact add(User owner,User person,Date now){
        TContact contact = new TContact();
        contact.owner = owner;
        contact.person = person;
        contact.create_at = now;
        contact.update_at = now;
        contact.save();
        return contact;
    }

    public static void deleteOrphans(User user){
        Ebean.createSqlUpdate("delete from contacts where owner_id = :owner_id and contact_id not in" +
                "(select b.contact_id from circles a,circle_members b where a.id = b.circle_id and a.author = :owner_id)")
                .setParameter("owner_id",user.id).execute();
    }

}
