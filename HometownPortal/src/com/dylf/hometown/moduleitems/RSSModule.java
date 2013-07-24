package com.dylf.hometown.moduleitems;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.RSS.RSSFeed;
import com.dylf.hometown.moduleitems.RSS.RSSItem;
import com.dylf.hometown.moduleitems.RSS.RSSRetriever;
import com.dylf.hometown.moduleitems.RSS.ShowDescription;

public class RSSModule extends AppModule {

  private boolean attached;
  private LinearLayout layout;
  private TextView title;
  private TextView date;
  private ListView list;
  
  private RSSFeed feed = null;
  Intent itemIntent;
  
  public final String RSSFEEDOFCHOICE = "http://events.newsherald.com/search?new=n&rss=1&sort=0&srad=60.0&srss=10&st=event&swhat=&swhen=&swhere=Panama+City%2C+FL";
  public final String DEBUGTAG = "RSSReader";
  
  private OnItemClickListener onItemClickListener;

  public RSSModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    
    // Keep but needs to be changed for RSS.
    generateRibbonItem(ModuleConfigs.EVENTS, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
    
    createListener(context);
    
    RSSRetriever rssRetriver = new RSSRetriever();
    //TODO **********  Ghetto attempt at fixing the Async issue. Fix this!
    AsyncTask<String, Void, String> rssRet = rssRetriver.execute(RSSFEEDOFCHOICE);
    try {
      Log.d("debug", rssRet.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    feed = rssRetriver.getRSSFeed();
    // *************
    updateDisplay(context);
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    // Create anchor layout.
    layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    
    // Create TextViews.
    title = new TextView(context);
    date  = new TextView(context);
    list  = new ListView(context);
    
    // Arrange TextViews.
    title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    date.setLayoutParams (new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    list.setLayoutParams (new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    
    // Attach TextViews.
    layout.addView(title);
    layout.addView(date);
    layout.addView(list);
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    layoutView.addView(layout);
    attached = true;
  }

  @Override
  protected void detachView() {
    layoutView.removeView(layout);
    attached = false;
  }

  @Override
  protected void doRibbonAction() {
    // No action currently.
  }

  
  private void createListener(final Context context) {
    onItemClickListener = new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Log.i(DEBUGTAG, "item clicked! [" + feed.getItem(position).getTitle() + "]");
        
        itemIntent = new Intent(context, ShowDescription.class);
        
        Bundle b = new Bundle();
        b.putString("title", feed.getItem(position).getTitle());
        b.putString("description", feed.getItem(position).getDescription());
        b.putString("link", feed.getItem(position).getLink());
        b.putString("pubdate", feed.getItem(position).getPubDate());

        itemIntent.putExtra("android.intent.extra.INTENT", b);

        //context.startActivityForResult(itemIntent, 0);
        context.startActivity(itemIntent);
      }
    };
  }
  
  private void updateDisplay(Context context) {
    if (feed == null) {
      title.setText("No RSS Feed Available");
      return;
    }
    title.setText(feed.getTitle());
    date.setText(feed.getPubDate());

    ArrayAdapter<RSSItem> adapter = new ArrayAdapter<RSSItem>(context, android.R.layout.simple_list_item_1, feed.getAllItems());
    list.setAdapter(adapter);
    list.setOnItemClickListener(onItemClickListener);
    list.setSelection(0);
  }
  
  
  
  @Override
  public void onResume() {
  }

  @Override
  public void onPause() {
  }

  @Override
  public void onDestroy() {
  }

  @Override
  public void onLowMemory() {
  }
}
