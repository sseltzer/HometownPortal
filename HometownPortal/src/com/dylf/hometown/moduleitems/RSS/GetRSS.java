package com.dylf.hometown.moduleitems.RSS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.util.Log;

public class GetRSS extends AsyncTask<String, Void, String> {

  private RSSFeed rssFeed;
  @Override
  protected String doInBackground(String... args) {
    RSSHandler theRssHandler = null;
    for (String arg : args) {
      try
      {
        // setup the url
         URL url = new URL(arg);

           // create the factory
           SAXParserFactory factory = SAXParserFactory.newInstance();
           // create a parser
           SAXParser parser = factory.newSAXParser();

           // create the reader (scanner)
           XMLReader xmlreader = parser.getXMLReader();
           // instantiate our handler
           theRssHandler = new RSSHandler();
           // assign our handler
           xmlreader.setContentHandler(theRssHandler);
           // get our data via the url class
           InputSource is = new InputSource(url.openStream());
           // perform the synchronous parse           
           xmlreader.parse(is);
           // get the results - should be a fully populated RSSFeed instance, or null on error
           
      }
      catch (Exception ee)
      {
        Log.d("debug", "Trace: " + ee.toString());
        ee.printStackTrace();
        // if we have a problem, simply return null
        return null;
      }
    }
    if (theRssHandler == null) return null;
    rssFeed = theRssHandler.getFeed();
    return "done";
  }
  
  public RSSFeed getRSSFeed() {
    return rssFeed;
  }

}