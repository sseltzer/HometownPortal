package com.dylf.hometown.moduleitems.rss;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.util.Log;

public class RSSRetriever extends AsyncTask<String, Void, String> {

  private RSSFeed rssFeed;

  @Override
  protected String doInBackground(String... args) {
    RSSHandler theRssHandler = null;
    for (String arg : args) {
      try {
        URL url = new URL(arg);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlreader = parser.getXMLReader();
        theRssHandler = new RSSHandler();
        xmlreader.setContentHandler(theRssHandler);
        InputSource is = new InputSource(url.openStream());
        xmlreader.parse(is);
      } catch (Exception ee) {
        Log.d("debug", "Trace: " + ee.toString());
        ee.printStackTrace();
        return null;
      }
    }
    //TODO Ghetto attempt at fixing the Async issue. Fix this!
    if (theRssHandler != null) {
      rssFeed = theRssHandler.getFeed();
      return "";
    }
    return null;
  }

  public RSSFeed getRSSFeed() {
    return rssFeed;
  }

}