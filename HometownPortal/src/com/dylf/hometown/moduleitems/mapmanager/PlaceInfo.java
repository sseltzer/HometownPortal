package com.dylf.hometown.moduleitems.mapmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class PlaceInfo implements InfoWindowAdapter {
  
  private LinearLayout layout;
  private TextView title;
  private TextView address;
  
  public PlaceInfo(Context context) {
    layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setBackgroundColor(Color.WHITE);
    
    title = new TextView(context);
    address = new TextView(context);
    
    title.setTextColor(Color.BLACK);
    address.setTextColor(Color.BLACK);
    
    layout.addView(title);
    layout.addView(address);
  }

  @Override
  public View getInfoContents(Marker marker) {
    title.setText(marker.getTitle());
    address.setText(marker.getSnippet());
    return layout;
  }

  @Override
  public View getInfoWindow(Marker marker) {
    title.setText(marker.getTitle());
    address.setText(marker.getSnippet());
    return layout;
  }
}