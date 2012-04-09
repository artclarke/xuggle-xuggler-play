package models;

import java.util.Collection;

import play.db.jpa.Model;

public class Tag 
{
  String category;
  String message;
  
  public static void create(
      Collection<Tag> tags,
      String aCategory, String aMessage)
  {
    final Tag t = new Tag();
    t.category = aCategory;
    t.message = aMessage;
    tags.add(t);
  }
}
