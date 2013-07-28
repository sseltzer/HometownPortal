package com.dylf.hometown.moduleitems;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.weather.CurrentRetriever;
import com.dylf.hometown.moduleitems.weather.OutlookRetriever;
import com.dylf.hometown.moduleitems.weather.WeatherViewManager;

public class WeatherModule extends AppModule {
  private boolean attached;
  private String city = "Panama_City";
  private String state = "FL";
  private String outlookStr = "http://api.wunderground.com/api/45405173084611a1/forecast/q/" + state + "/" + city + ".json";
  private String currentStr = "http://api.wunderground.com/api/45405173084611a1/forecast/geolookup/conditions/q/" + state + "/" + city + ".json";
  private WeatherViewManager wVm;
  
  public WeatherModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.WEATHER, router.getListener());
    router.addCallback(getRibbonItem().getBID(), this);
    generateView(context, savedInstanceState);
    OutlookRetriever oRetriever = new OutlookRetriever(wVm);
    CurrentRetriever cRetriever = new CurrentRetriever(wVm);
    
    oRetriever.execute(outlookStr);
    cRetriever.execute(currentStr);
  }
  
  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    wVm = WeatherViewManager.getInstance(context);
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    wVm.requestAttach(layoutView);
    attached = true;
  }

  @Override
  protected void detachView() {
    wVm.requestDetach(layoutView);
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
