package com.dylf.hometown.moduleitems;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.weather.OutlookRetriever;
import com.dylf.hometown.moduleitems.weather.WeatherViewManager;

public class WeatherModule extends AppModule {
  private boolean attached;
  private String city = "Panama_City";
  private String state = "FL";
  private String cityState = "";
  private String outlookStr = "http://api.wunderground.com/api/45405173084611a1/forecast/q/" + state + "/" + city + ".json";
  private WeatherViewManager wVm;
  
  public WeatherModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.WEATHER, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
    wVm = WeatherViewManager.getInstance(context);
    OutlookRetriever oRetriever = new OutlookRetriever(wVm);
    oRetriever.execute(outlookStr);
  }
  
  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    layoutView.addView(wVm.getOutlookView());
    attached = true;
  }

  @Override
  protected void detachView() {
    layoutView.removeView(wVm.getOutlookView());
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
