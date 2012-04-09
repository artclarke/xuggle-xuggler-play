package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Tag extends Model
{
  String category;
  @Column(length=2048)
  String message;
  
  public static void create(String aCategory, String aMessage)
  {
    Tag t = new Tag();
    t.category = aCategory;
    t.message = aMessage;
    t.create();
  }
}
