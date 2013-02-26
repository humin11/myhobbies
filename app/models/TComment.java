package models;

import com.avaje.ebean.annotation.Where;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="comments")
public class TComment extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String content;

    @ManyToOne
    public TUser author;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date create_at;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date update_at;

    //e.g. modified or not
    public String status;

    @OneToMany(mappedBy = "likeable_id")
    @Where(clause = "likeable_type='COMMENT'")
    public List<TLike> likes;

    @OneToMany(mappedBy = "source_id")
    @Where(clause = "source_type='COMMENT'")
    public List<TMention> mentions;

    public Long commentable_id;

    //e.g. POST,COMMENT,PHOTO
    public String commentable_type;

}
