package controllers;

import com.xuggle.xuggler.Global;

import play.mvc.Controller;

public class Xuggle extends Controller
{
  public static void index()
  {
    String versionStr = Global.getAVCodecVersionStr();
    renderText("Xuggler version is: " + versionStr);
  }
}
