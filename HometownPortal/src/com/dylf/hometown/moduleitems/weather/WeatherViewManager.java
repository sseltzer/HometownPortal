package com.dylf.hometown.moduleitems.weather;

import java.util.EnumMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dylf.hometown.R;
import com.dylf.hometown.R.color;

public class WeatherViewManager {

  private static WeatherViewManager instance = null;
  
  private final int LPADDING = 10;
  private final int SPADDING = 5;
  private final int XLTSIZE = 30;
  private final int LTSIZE = 12;
  private final int STSIZE = 8;
  private final int TEXTCOLOR = Color.WHITE;
  
  private EnumMap<CurrentObject, View> currentMap;
  private EnumMap<OutlookObject, View> outlookMap;
  
  private LinearLayout anchor;
  private RelativeLayout displayedView;
  
  private RelativeLayout currentView = null;
  private RelativeLayout outlookView = null;
  
  private OnClickListener imageClickListener;
  public OnClickListener buildImageClickListener(final Context context) {
    return new OnClickListener() {
      @Override
      public void onClick(View v) {
        Uri csurl = Uri.parse("http://www.wunderground.com/?apiref=9cccc149e962b2c9");
        Intent webLaunch = new Intent(Intent.ACTION_VIEW, csurl);
        context.startActivity(webLaunch);
       }
    };
  }
  public OnClickListener buttonClickListener;
  public OnClickListener buildButtonClickListener() {
    return new OnClickListener() {
      @Override
      public void onClick(View v) {
        requestDetach(anchor);
        if (v.equals(currentMap.get(CurrentObject.BUTTON))) displayedView = outlookView;
        else if (v.equals(outlookMap.get(OutlookObject.BUTTON))) displayedView = currentView;
        requestAttach(anchor);
      }
    };
  }
  
  private WeatherViewManager(Context context) {
    imageClickListener = buildImageClickListener(context);
    buttonClickListener = buildButtonClickListener();
    generateCurrentView(context);
    generateOutlookView(context);
    displayedView = currentView;
  }
  private void generateCurrentView(Context context) {
    currentMap = new EnumMap<CurrentObject, View>(CurrentObject.class);
    
    currentView = new RelativeLayout(context);
    currentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    currentView.setBackgroundColor(color.weather_back);
    currentView.setPadding(LPADDING, LPADDING, LPADDING, LPADDING);
    
    RelativeLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams linearParams;
    
    
    ImageView currentImage = new ImageView(context);
    currentImage.setId(R.id.currentImage);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    currentImage.setLayoutParams(layoutParams);
    currentImage.setBackgroundResource(R.drawable.wundergroundlogowhtblubkgd);
    currentImage.setOnClickListener(imageClickListener);
    currentMap.put(CurrentObject.IMAGE, currentImage);
    currentView.addView(currentImage);
    

    Button changeButton = new Button(context);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    layoutParams.addRule(RelativeLayout.ABOVE, R.id.currentImage);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.currentImage);
    layoutParams.addRule(RelativeLayout.ALIGN_RIGHT, R.id.currentImage);
    changeButton.setLayoutParams(layoutParams);
    changeButton.setOnClickListener(buttonClickListener);
    changeButton.setText("Show Weather Outlook");
    currentView.addView(changeButton);
    currentMap.put(CurrentObject.BUTTON, changeButton);
    
    LinearLayout contentLayout = new LinearLayout(context);
    contentLayout.setOrientation(LinearLayout.VERTICAL);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    contentLayout.setLayoutParams(linearParams);
    
    
    TextView currentLabel = new TextView(context);
    currentLabel.setId(R.id.currentLabel);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    layoutParams.addRule(RelativeLayout.ALIGN_RIGHT, R.id.temp);
    currentLabel.setLayoutParams(layoutParams);
    currentLabel.setPadding(LPADDING, LPADDING, LPADDING, LPADDING);
    currentLabel.setTextColor(TEXTCOLOR);
    currentLabel.setTextSize(LTSIZE);
    currentMap.put(CurrentObject.CURRENT, currentLabel);
    contentLayout.addView(currentLabel);
    
    
    LinearLayout iconLayout = new LinearLayout(context);
    iconLayout.setOrientation(LinearLayout.HORIZONTAL);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    iconLayout.setLayoutParams(linearParams);
    
    ImageView weathericon = new ImageView(context);
    weathericon.setId(R.id.weathericon);
    layoutParams = new RelativeLayout.LayoutParams(150, 150);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.currentLabel);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.currentLabel);
    weathericon.setLayoutParams(layoutParams);
    weathericon.setBackgroundResource(R.drawable.ic_launcher);
    currentMap.put(CurrentObject.WICON, weathericon);
    iconLayout.addView(weathericon);
    
    LinearLayout tempLayout = new LinearLayout(context);
    tempLayout.setOrientation(LinearLayout.VERTICAL);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    tempLayout.setLayoutParams(linearParams);
    
    TextView temp = new TextView(context);
    currentLabel.setId(R.id.temp);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    temp.setLayoutParams(linearParams);
    temp.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    temp.setTextColor(TEXTCOLOR);
    temp.setTextSize(XLTSIZE);
    currentMap.put(CurrentObject.TEMP, temp);
    tempLayout.addView(temp);
    
    TextView curDesc = new TextView(context);
    currentLabel.setId(R.id.curDesc);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    curDesc.setLayoutParams(linearParams);
    curDesc.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    curDesc.setTextColor(TEXTCOLOR);
    curDesc.setTextSize(XLTSIZE);
    currentMap.put(CurrentObject.CURDESC, curDesc);
    tempLayout.addView(curDesc);
    
    iconLayout.addView(tempLayout);
    contentLayout.addView(iconLayout);
    
    LinearLayout feelsLayout = new LinearLayout(context);
    feelsLayout.setOrientation(LinearLayout.HORIZONTAL);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    feelsLayout.setLayoutParams(linearParams);
    
    TextView feels = new TextView(context);
    currentLabel.setId(R.id.feels);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
    feels.setLayoutParams(linearParams);
    feels.setTextColor(TEXTCOLOR);
    currentMap.put(CurrentObject.FEELS, feels);
    feelsLayout.addView(feels);
    
    TextView humid = new TextView(context);
    currentLabel.setId(R.id.humid);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
    humid.setLayoutParams(linearParams);
    humid.setTextColor(TEXTCOLOR);
    currentMap.put(CurrentObject.HUMID, humid);
    feelsLayout.addView(humid);
    
    contentLayout.addView(feelsLayout);
    
    LinearLayout windLayout = new LinearLayout(context);
    windLayout.setOrientation(LinearLayout.HORIZONTAL);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    windLayout.setLayoutParams(linearParams);
    
    TextView windDir = new TextView(context);
    currentLabel.setId(R.id.windDir);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
    windDir.setLayoutParams(linearParams);
    windDir.setTextColor(TEXTCOLOR);
    currentMap.put(CurrentObject.WINDDIR, windDir);
    windLayout.addView(windDir);
    
    TextView windSpd = new TextView(context);
    currentLabel.setId(R.id.windSpd);
    linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
    windSpd.setLayoutParams(linearParams);
    windSpd.setTextColor(TEXTCOLOR);
    currentMap.put(CurrentObject.WINDSPD, windSpd);
    windLayout.addView(windSpd);
    
    contentLayout.addView(windLayout);
    currentView.addView(contentLayout);
  }
  private void generateOutlookView(Context context) {
    outlookMap = new EnumMap<OutlookObject, View>(OutlookObject.class);
    
    outlookView = new RelativeLayout(context);
    outlookView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    outlookView.setBackgroundColor(color.weather_back);
    outlookView.setPadding(LPADDING, LPADDING, LPADDING, LPADDING);
    
    RelativeLayout.LayoutParams layoutParams;
    
    TextView outlookLabel = new TextView(context);
    outlookLabel.setId(R.id.outlookLabel);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    outlookLabel.setLayoutParams(layoutParams);
    outlookLabel.setPadding(LPADDING, LPADDING, LPADDING, LPADDING);
    outlookLabel.setText("Weather Outlook for");
    outlookLabel.setTextColor(TEXTCOLOR);
    outlookLabel.setTextSize(LTSIZE);
    outlookMap.put(OutlookObject.OUTLOOK, outlookLabel);
    outlookView.addView(outlookLabel);
    
    ImageView outlookImage = new ImageView(context);
    outlookImage.setId(R.id.outlookImage);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    outlookImage.setLayoutParams(layoutParams);
    outlookImage.setBackgroundResource(R.drawable.wundergroundlogowhtblubkgd);
    outlookImage.setOnClickListener(imageClickListener);
    outlookMap.put(OutlookObject.IMAGE, outlookImage);
    outlookView.addView(outlookImage);
    
    Button changeButton = new Button(context);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    layoutParams.addRule(RelativeLayout.ABOVE, R.id.outlookImage);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.outlookImage);
    layoutParams.addRule(RelativeLayout.ALIGN_RIGHT, R.id.outlookImage);
    changeButton.setLayoutParams(layoutParams);
    changeButton.setOnClickListener(buttonClickListener);
    changeButton.setText("Show Current Weather");
    outlookMap.put(OutlookObject.BUTTON, changeButton);
    outlookView.addView(changeButton);
    
    TextView segHead1 = new TextView(context);
    segHead1.setId(R.id.segHead1);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.outlookLabel);
    segHead1.setLayoutParams(layoutParams);
    segHead1.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    segHead1.setTextColor(TEXTCOLOR);
    outlookMap.put(OutlookObject.HEAD1, segHead1);
    outlookView.addView(segHead1);
    
    ImageView icon1 = new ImageView(context);
    icon1.setId(R.id.icon1);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.segHead1);
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
    desc1.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    desc1.setTextColor(TEXTCOLOR);
    desc1.setTextSize(STSIZE);
    outlookMap.put(OutlookObject.DESC1, desc1);
    outlookView.addView(desc1);
    
    TextView segHead2 = new TextView(context);
    segHead2.setId(R.id.segHead2);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.icon1);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.icon1);
    segHead2.setLayoutParams(layoutParams);
    segHead2.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    segHead2.setTextColor(TEXTCOLOR);
    outlookMap.put(OutlookObject.HEAD2, segHead2);
    outlookView.addView(segHead2);
    
    ImageView icon2 = new ImageView(context);
    icon2.setId(R.id.icon2);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.segHead2);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead2);
    icon2.setLayoutParams(layoutParams);
    icon2.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
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
    desc2.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    desc2.setTextColor(TEXTCOLOR);
    desc2.setTextSize(STSIZE);
    outlookMap.put(OutlookObject.DESC2, desc2);
    outlookView.addView(desc2);
    
    TextView segHead3 = new TextView(context);
    segHead3.setId(R.id.segHead3);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.icon2);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.icon2);
    segHead3.setLayoutParams(layoutParams);
    segHead3.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    segHead3.setTextColor(TEXTCOLOR);
    outlookMap.put(OutlookObject.HEAD3, segHead3);
    outlookView.addView(segHead3);
    
    ImageView icon3 = new ImageView(context);
    icon3.setId(R.id.icon3);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.segHead3);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead3);
    icon3.setLayoutParams(layoutParams);
    icon3.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
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
    desc3.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    desc3.setTextColor(TEXTCOLOR);
    desc3.setTextSize(STSIZE);
    outlookMap.put(OutlookObject.DESC3, desc3);
    outlookView.addView(desc3);
    
    TextView segHead4 = new TextView(context);
    segHead4.setId(R.id.segHead4);
    layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.icon3);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.icon3);
    segHead4.setLayoutParams(layoutParams);
    segHead4.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    segHead4.setTextColor(TEXTCOLOR);
    outlookMap.put(OutlookObject.HEAD4, segHead4);
    outlookView.addView(segHead4);
    
    ImageView icon4 = new ImageView(context);
    icon4.setId(R.id.icon4);
    layoutParams = new RelativeLayout.LayoutParams(50, 50);
    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.segHead4);
    layoutParams.addRule(RelativeLayout.BELOW, R.id.segHead4);
    icon4.setLayoutParams(layoutParams);
    icon4.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
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
    desc4.setPadding(SPADDING, SPADDING, SPADDING, SPADDING);
    desc4.setTextColor(TEXTCOLOR);
    desc4.setTextSize(STSIZE);
    outlookMap.put(OutlookObject.DESC4, desc4);
    outlookView.addView(desc4);
  }
  
  public static WeatherViewManager getInstance(Context context) {
    if (instance == null) instance = new WeatherViewManager(context);
    return instance;
  }
  
  public View getCurrentViewItem(CurrentObject object) {
    return currentMap.get(object);
  }
  public View getOutlookViewItem(OutlookObject object) {
    return outlookMap.get(object);
  }
  
  public void requestAttach(LinearLayout anchor) {
    this.anchor = anchor;
    anchor.addView(displayedView);
  }
  public void requestDetach(LinearLayout anchor) {
    this.anchor = anchor;
    anchor.removeView(displayedView);
  }
  
  
  public enum OutlookObject {
    BUTTON,
    OUTLOOK,
    IMAGE,
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
  public enum CurrentObject {
    BUTTON,
    IMAGE,
    CURRENT,
    WICON,
    TEMP,
    CURDESC,
    FEELS,
    HUMID,
    WINDDIR,
    WINDSPD
  }
}
