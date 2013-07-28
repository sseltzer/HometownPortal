package com.dylf.hometown.moduleitems.weather;

import java.util.Collection;
import java.util.EnumMap;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dylf.hometown.R;
import com.dylf.hometown.R.color;

public class WeatherViewManager {

  private static WeatherViewManager instance = null;
  
  
  private EnumMap<OutlookObject, View> outlookMap;
  private RelativeLayout outlookView = null;
  
  private WeatherViewManager(Context context) {
    
    outlookMap = new EnumMap<OutlookObject, View>(OutlookObject.class);
    
    outlookView = new RelativeLayout(context);
    outlookView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    outlookView.setBackgroundColor(color.weather_back);
    outlookView.setPadding(10, 10, 10, 10);
    
    RelativeLayout.LayoutParams layoutParams;
    
    TextView outlookLabel = new TextView(context);
    outlookLabel.setId(R.id.outlookLabel);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    outlookLabel.setLayoutParams(layoutParams);
    outlookLabel.setPadding(10, 10, 10, 10);
    outlookLabel.setText("Weather Outlook for");
    outlookLabel.setTextColor(Color.WHITE);
    outlookLabel.setTextSize(12);
    outlookMap.put(OutlookObject.LABEL, outlookLabel);
    outlookView.addView(outlookLabel);
    
    ImageView imageView1 = new ImageView(context);
    imageView1.setId(R.id.imageView1);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    imageView1.setLayoutParams(layoutParams);
    imageView1.setBackgroundResource(R.drawable.wundergroundlogowhtblubkgd);
    //TODO set onClick
    outlookMap.put(OutlookObject.IMAGE1, imageView1);
    outlookView.addView(imageView1);
    
    TextView segHead1 = new TextView(context);
    segHead1.setId(R.id.segHead1);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.outlookLabel);
    segHead1.setLayoutParams(layoutParams);
    segHead1.setPadding(5, 5, 5, 5);
    segHead1.setTextColor(Color.WHITE);
    outlookMap.put(OutlookObject.HEAD1, segHead1);
    outlookView.addView(segHead1);
    
    ImageView icon1 = new ImageView(context);
    icon1.setId(R.id.icon1);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.segHead1);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead1);
    icon1.setLayoutParams(layoutParams);
    icon1.setBackgroundResource(R.drawable.ic_launcher);
    outlookMap.put(OutlookObject.ICON1, icon1);
    outlookView.addView(icon1);
    
    TextView desc1 = new TextView(context);
    desc1.setId(R.id.desc1);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.icon1);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead1);
    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.icon1);
    desc1.setLayoutParams(layoutParams);
    desc1.setPadding(5, 5, 5, 5);
    desc1.setTextColor(Color.WHITE);
    desc1.setTextSize(8);
    outlookMap.put(OutlookObject.DESC1, desc1);
    outlookView.addView(desc1);
    
    TextView segHead2 = new TextView(context);
    segHead2.setId(R.id.segHead2);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.icon1);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.icon1);
    segHead2.setLayoutParams(layoutParams);
    segHead2.setPadding(5, 5, 5, 5);
    segHead2.setTextColor(Color.WHITE);
    outlookMap.put(OutlookObject.HEAD2, segHead2);
    outlookView.addView(segHead2);
    
    ImageView icon2 = new ImageView(context);
    icon2.setId(R.id.icon2);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.segHead2);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead2);
    icon2.setLayoutParams(layoutParams);
    icon2.setPadding(5, 5, 5, 5);
    icon2.setBackgroundResource(R.drawable.ic_launcher);
    outlookMap.put(OutlookObject.ICON2, icon2);
    outlookView.addView(icon2);

    TextView desc2 = new TextView(context);
    desc2.setId(R.id.desc2);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.icon2);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    layoutParams.addRule(RelativeLayout.ALIGN_TOP, R.id.icon2);
    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.icon2);
    desc2.setLayoutParams(layoutParams);
    desc2.setPadding(5, 5, 5, 5);
    desc2.setTextColor(Color.WHITE);
    desc2.setTextSize(8);
    outlookMap.put(OutlookObject.DESC2, desc2);
    outlookView.addView(desc2);
    
    TextView segHead3 = new TextView(context);
    segHead3.setId(R.id.segHead3);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.icon2);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.icon2);
    segHead3.setLayoutParams(layoutParams);
    segHead3.setPadding(5, 5, 5, 5);
    segHead3.setTextColor(Color.WHITE);
    outlookMap.put(OutlookObject.HEAD3, segHead3);
    outlookView.addView(segHead3);
    
    ImageView icon3 = new ImageView(context);
    icon3.setId(R.id.icon3);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.segHead3);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead3);
    icon3.setLayoutParams(layoutParams);
    icon3.setPadding(5, 5, 5, 5);
    icon3.setBackgroundResource(R.drawable.ic_launcher);
    outlookMap.put(OutlookObject.ICON3, icon3);
    outlookView.addView(icon3);
    
    TextView desc3 = new TextView(context);
    desc3.setId(R.id.desc3);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.icon3);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.desc2);
    layoutParams.addRule(RelativeLayout.ALIGN_TOP, R.id.icon3);
    desc3.setLayoutParams(layoutParams);
    desc3.setPadding(5, 5, 5, 5);
    desc3.setTextColor(Color.WHITE);
    desc3.setTextSize(8);
    outlookMap.put(OutlookObject.DESC3, desc3);
    outlookView.addView(desc3);
    
    TextView segHead4 = new TextView(context);
    segHead4.setId(R.id.segHead4);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.icon3);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.icon3);
    segHead4.setLayoutParams(layoutParams);
    segHead4.setPadding(5, 5, 5, 5);
    segHead4.setTextColor(Color.WHITE);
    outlookMap.put(OutlookObject.HEAD4, segHead4);
    outlookView.addView(segHead4);
    
    ImageView icon4 = new ImageView(context);
    icon4.setId(R.id.icon4);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.segHead4);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead4);
    icon4.setLayoutParams(layoutParams);
    icon4.setPadding(5, 5, 5, 5);
    icon4.setBackgroundResource(R.drawable.ic_launcher);
    outlookMap.put(OutlookObject.ICON4, icon4);
    outlookView.addView(icon4);
    
    TextView desc4 = new TextView(context);
    desc4.setId(R.id.desc4);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.icon4);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead4);
    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.icon4);
    desc4.setLayoutParams(layoutParams);
    desc4.setPadding(5, 5, 5, 5);
    desc4.setTextColor(Color.WHITE);
    desc4.setTextSize(8);
    outlookMap.put(OutlookObject.DESC4, desc4);
    outlookView.addView(desc4);
  }
  
  public static WeatherViewManager getInstance(Context context) {
    if (instance == null) instance = new WeatherViewManager(context);
    return instance;
  }
  
  public View getOutlookView() {
    return outlookView;
  }
  public View getOutlookViewItem(OutlookObject outlookObject) {
    return outlookMap.get(outlookObject);
  }
  
  public enum OutlookObject {
    LABEL,
    IMAGE1,
    HEAD1,
    ICON1,
    DESC1,
    HEAD2,
    ICON2,
    DESC2,
    HEAD3,
    ICON3,
    DESC3,
    HEAD4,
    ICON4,
    DESC4
  }
}
