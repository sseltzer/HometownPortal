package com.dylf.hometown.moduleitems.RSS;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener; 
import android.util.Log;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import org.xml.sax.XMLReader;

import android.content.Intent;

import com.dylf.hometown.R;
import com.dylf.hometown.moduleitems.RSS.ShowDescription;

public class RSSReader extends Activity implements OnItemClickListener
{

	public final String RSSFEEDOFCHOICE = "http://events.newsherald.com/search?new=n&rss=1&sort=0&srad=60.0&srss=10&st=event&swhat=&swhen=&swhere=Panama+City%2C+FL";
	
	public final String tag = "RSSReader";
	private RSSFeed feed = null;
	
	/** Called when the activity is first created. */

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.rss);
        
        GetRSS getRSS = new GetRSS();
        
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

    

    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
    	
    	menu.add(0,0,0, "Choose RSS Feed");
    	menu.add(0, 1, 1, "Refresh");
    	Log.i(tag,"onCreateOptionsMenu");
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
        case 0:
        	Log.i(tag,"Set RSS Feed");
            return true;
        case 1:
        	Log.i(tag,"Refreshing RSS Feed");
            return true;
        }
        return false;
    }
    
    
    private void UpdateDisplay()
    {
        TextView feedtitle = (TextView) findViewById(R.id.feedtitle);
        TextView feedpubdate = (TextView) findViewById(R.id.feedpubdate);
        ListView itemlist = (ListView) findViewById(R.id.itemlist);
  
        
        if (feed == null)
        {
        	feedtitle.setText("No RSS Feed Available");
        	return;
        }
        
        feedtitle.setText(feed.getTitle());
        feedpubdate.setText(feed.getPubDate());

        ArrayAdapter<RSSItem> adapter = new ArrayAdapter<RSSItem>(this,android.R.layout.simple_list_item_1,feed.getAllItems());

        itemlist.setAdapter(adapter);
        
        itemlist.setOnItemClickListener(this);
        
        itemlist.setSelection(0);
        
    }
    
    
     public void onItemClick(AdapterView parent, View v, int position, long id)
     {
    	 Log.i(tag,"item clicked! [" + feed.getItem(position).getTitle() + "]");

    	 Intent itemintent = new Intent(this,ShowDescription.class);
         
    	 Bundle b = new Bundle();
    	 b.putString("title", feed.getItem(position).getTitle());
    	 b.putString("description", feed.getItem(position).getDescription());
    	 b.putString("link", feed.getItem(position).getLink());
    	 b.putString("pubdate", feed.getItem(position).getPubDate());
    	 
    	 itemintent.putExtra("android.intent.extra.INTENT", b);
         
         startActivityForResult(itemintent,0);
     }
    
}