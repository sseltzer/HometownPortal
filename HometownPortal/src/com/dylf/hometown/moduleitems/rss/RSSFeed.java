package com.dylf.hometown.moduleitems.rss;

import java.util.List;
import java.util.Vector;

import com.dylf.hometown.moduleitems.rss.RSSItem;

public class RSSFeed {
  private String title;
  private String pubdate;
  private List<RSSItem> items;
  
  public RSSFeed() {
    items = new Vector<RSSItem>();
  }

  public int addItem(RSSItem item) {
    items.add(item);
    return items.size();
  }

  public RSSItem getItem(int location) {
    return items.get(location);
  }

  public List<RSSItem> getAllItems() {
    return items;
  }

  public int getItemCount() {
    return items.size();
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPubDate(String pubdate) {
    this.pubdate = pubdate;
  }

  public String getTitle() {
    return title;
  }

  public String getPubDate() {
    return pubdate;
  }
}
