package com.dylf.hometown.moduleitems.RSS;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RSSReader extends Activity implements OnItemClickListener {

  public final String RSSFEEDOFCHOICE = "http://events.newsherald.com/search?new=n&rss=1&sort=0&srad=60.0&srss=10&st=event&swhat=&swhen=&swhere=Panama+City%2C+FL";

  public final String tag = "RSSReader";
  private RSSFeed feed = null;

  LinearLayout layout;
  TextView title;
  TextView date;
  ListView list;

  /** Called when the activity is first created. */

  public RSSReader(Context context, TextView title, TextView date, ListView list) {

    this.title = title;
    this.date = date;
    this.list = list;

    RSSRetriever getRSS = new RSSRetriever();

    AsyncTask<String, Void, String> rssRet = getRSS.execute(RSSFEEDOFCHOICE);
    try {
      Log.d("debug", rssRet.get());
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    feed = getRSS.getRSSFeed();
    UpdateDisplay();

  }

  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);

    menu.add(0, 0, 0, "Choose RSS Feed");
    menu.add(0, 1, 1, "Refresh");
    Log.i(tag, "onCreateOptionsMenu");
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case 0:
      Log.i(tag, "Set RSS Feed");
      return true;
    case 1:
      Log.i(tag, "Refreshing RSS Feed");
      return true;
    }
    return false;
  }

  private void UpdateDisplay() {
    if (feed == null) {
      title.setText("No RSS Feed Available");
      return;
    }

    title.setText(feed.getTitle());
    date.setText(feed.getPubDate());

    ArrayAdapter<RSSItem> adapter = new ArrayAdapter<RSSItem>(this,
        android.R.layout.simple_list_item_1, feed.getAllItems());

    list.setAdapter(adapter);

    list.setOnItemClickListener(this);

    list.setSelection(0);

  }

  public void onItemClick(AdapterView parent, View v, int position, long id) {
    Log.i(tag, "item clicked! [" + feed.getItem(position).getTitle() + "]");

    Intent itemintent = new Intent(this, ShowDescription.class);

    Bundle b = new Bundle();
    b.putString("title", feed.getItem(position).getTitle());
    b.putString("description", feed.getItem(position).getDescription());
    b.putString("link", feed.getItem(position).getLink());
    b.putString("pubdate", feed.getItem(position).getPubDate());

    itemintent.putExtra("android.intent.extra.INTENT", b);

    startActivityForResult(itemintent, 0);
  }

}