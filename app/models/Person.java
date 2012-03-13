package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Person extends Model {

    public String lastName;
    public String firstName;

}
