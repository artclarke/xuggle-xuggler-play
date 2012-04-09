package controllers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import models.Tag;

import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IError;
import com.xuggle.xuggler.IMetaData;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.demos.GetContainerInfo;

import play.mvc.Controller;

public class Xuggle extends Controller
{
  public static void index()
  {
    final List<Tag> tags = new LinkedList<Tag>();
    render(tags);
  }
  private static void createErrorTag(Collection<Tag> tags, int retval, String message)
  {
    final IError e = IError.make(retval);
    Tag.create(tags, "ERROR", e.toString() + ": " + message);
    e.delete();
    
  }
  public static void query(String url)
  {
    final List<Tag> tags = new LinkedList<Tag>();

    try {
      if (url==null || url.length() <=0) {
        Tag.create(tags, "ERROR", "Invalid URL");
        return;
      }

      Tag.create(tags, "URL", url);

      final IContainer container = IContainer.make();

      int retval;
      retval = container.open(url,IContainer.Type.READ, null);
      if (retval < 0) {
        createErrorTag(tags, retval, url);
        return;
      }
      retval = container.queryStreamMetaData();
      if (retval < 0) {
        createErrorTag(tags, retval, url);
        return;
      }

      Tag.create(tags, "CONTAINER", container.toString());
      final int numStreams = container.getNumStreams();
      for(int i = 0; i < numStreams; i++)
      {
        final IStream stream = container.getStream(i);
        Tag.create(tags, "STREAM", stream.toString());
        stream.delete();
      }
      final IMetaData metadata = container.getMetaData();
      final Collection<String> keys = metadata.getKeys();
      for(String key : keys)
      {
        String value = metadata.getValue(key);
        Tag.create(tags, "META", "["+key+"] = [" + value + "]");
      }
      metadata.delete();
      container.close();
      container.delete();
    } finally {
      renderTemplate("Xuggle/index.html", tags);
    }
  }
}
