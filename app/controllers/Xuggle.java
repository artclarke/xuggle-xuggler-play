package controllers;

import java.util.Collection;
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
    //    String versionStr = Global.getAVCodecVersionStr();
    //    renderText("Xuggler version is: " + versionStr);
    final List<Tag> tags = Tag.findAll();
    render(tags);
  }
  private static void createErrorTag(int retval, String message)
  {
    final IError e = IError.make(retval);
    Tag.create("ERROR", e.toString() + ": " + message);
    e.delete();
    
  }
  public static void query(String url)
  {
    try {
      // clear the old tags
      Tag.deleteAll();

      if (url==null || url.length() <=0) {
        Tag.create("ERROR", "Invalid URL");
        return;
      }

      Tag.create("URL", url);

      final IContainer container = IContainer.make();

      int retval;
      retval = container.open(url,IContainer.Type.READ, null);
      if (retval < 0) {
        createErrorTag(retval, url);
        return;
      }
      retval = container.queryStreamMetaData();
      if (retval < 0) {
        createErrorTag(retval, url);
        return;
      }

      Tag.create("CONTAINER", container.toString());
      final int numStreams = container.getNumStreams();
      for(int i = 0; i < numStreams; i++)
      {
        final IStream stream = container.getStream(i);
        Tag.create("STREAM", stream.toString());
        stream.delete();
      }
      final IMetaData metadata = container.getMetaData();
      final Collection<String> keys = metadata.getKeys();
      for(String key : keys)
      {
        String value = metadata.getValue(key);
        Tag.create("META", "["+key+"] = [" + value + "]");
      }
      metadata.delete();
      container.close();
      container.delete();
    } finally {
      index();
    }
  }
}
