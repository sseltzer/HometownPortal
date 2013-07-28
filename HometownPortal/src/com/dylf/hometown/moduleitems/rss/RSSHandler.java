package com.dylf.hometown.moduleitems.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RSSHandler extends DefaultHandler {

  private RSSFeed feed;
  private RSSItem item;
  private HandleState curState = HandleState.RSS_CHANNEL;

  public RSSFeed getFeed() {
    return feed;
  }

  public void startDocument() throws SAXException {
    feed = new RSSFeed();
    item = new RSSItem();
  }

  public void endDocument() throws SAXException {
  }

  public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    curState = HandleState.getStateFromString(localName);
    switch(curState) {
      case RSS_IMAGE:
        feed.setTitle(item.getTitle());
        feed.setPubDate(item.getPubDate());
        curState = HandleState.RSS_CHANNEL;
        return;
      case RSS_ITEM:
        item = new RSSItem();
        curState = HandleState.RSS_ITEM;
        return;
      case RSS_CHANNEL:
      case RSS_TITLE:
      case RSS_DESCRIPTION:
      case RSS_LINK:
      case RSS_CATEGORY:
      case RSS_PUBDATE:
      default:
        curState = HandleState.getStateFromString(localName);
        return;
    }
  }

  public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    if (localName.equals(HandleState.RSS_ITEM.toString())) feed.addItem(item);
  }

  public void characters(char ch[], int start, int length) {
    String theString = new String(ch, start, length);
    //Log.i("RSSReader", "characters[" + theString + "]");
    
    switch(curState) {
      case RSS_TITLE:
        item.setTitle(theString);
        curState = HandleState.RSS_CHANNEL;
        break;  
      case RSS_LINK:
        item.setLink(theString);
        curState = HandleState.RSS_CHANNEL;
        break;
      case RSS_DESCRIPTION:
        item.setDescription(theString);
        curState = HandleState.RSS_CHANNEL;
        break;
      case RSS_CATEGORY:
        item.setCategory(theString);
        curState = HandleState.RSS_CHANNEL;
        break;
      case RSS_PUBDATE:
        item.setPubDate(theString);
        curState = HandleState.RSS_CHANNEL;
        break;
      default:
        break;
    }
  }
  
  private enum HandleState {
    RSS_CHANNEL("channel"),
    RSS_IMAGE("image"),
    RSS_ITEM("item"),
    RSS_TITLE("title"),
    RSS_LINK("link"),
    RSS_DESCRIPTION("description"),
    RSS_CATEGORY("category"),
    RSS_PUBDATE("pubDate");
    
    private String stateStr;
    private HandleState(String stateStr) {
      this.stateStr = stateStr;
    }
    public String toString() {
      return stateStr;
    }
    public static HandleState getStateFromString(String stateStr) {
      for(HandleState state : HandleState.values()) if (stateStr.equals(state.toString())) return state;
      return RSS_CHANNEL;
    }
  }
}