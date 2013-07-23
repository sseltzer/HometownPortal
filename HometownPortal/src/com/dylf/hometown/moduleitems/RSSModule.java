package com.dylf.hometown.moduleitems;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dylf.hometown.R;
import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.MapManager.GetPlace;
import com.dylf.hometown.moduleitems.RSS.GetRSS;
import com.dylf.hometown.moduleitems.RSS.RSSItem;
import com.dylf.hometown.moduleitems.RSS.RSSReader;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class RSSModule extends AppModule{

  //private final String MAPKEY;
  //private final String PLACESKEY;
  
  boolean attached;
  LinearLayout layout;
  TextView title;
  TextView date;
  ListView list;
  RSSReader rssreader;
  
  
  //String normal;
  //String hybrid;
  //String satellite;
  
  public RSSModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
   //MAPKEY = context.getString(R.string.mapkey);
   //PLACESKEY = context.getString(R.string.placeskey);
    attached = false;
    generateRibbonItem(ModuleConfigs.MAPS, router.getListener()); //keep but needs to be changed for RSS
    generateView(context, savedInstanceState); //keep
    router.addCallback(getRibbonItem().getBID(), this); //keep
    
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
      
	  layout = new LinearLayout(context);
      layout.setOrientation(LinearLayout.VERTICAL);
      title = new TextView(context);
      date = new TextView(context);
      list = new ListView(context);

      title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
      date.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
      list.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
      layout.addView(title);
      layout.addView(date);
      layout.addView(list);

	  rssreader = new RSSReader(context, title, date, list);
   
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
