package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TGroup extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public Long owner;

    @Constraints.Required
    public String name;

    //e.g. Friend,Custom,Others
    public String type;
}
