package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="albums")
public class TAlbum extends Model {

    @Id
    @GeneratedValue
    public Long id;

    public TUser owner;

    public String name;

    @OneToMany(mappedBy = "album")
    public List<TPhoto> photos;

}
