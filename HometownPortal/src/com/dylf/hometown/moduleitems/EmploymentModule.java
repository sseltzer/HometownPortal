package com.dylf.hometown.moduleitems;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.webkit.WebView;


import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleConfigs;


public class EmploymentModule extends AppModule {

  boolean attached;
  WebView webView;
  
  public EmploymentModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.EMPLOYMENT, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    String url = "http://api.indeed.com/ads/apisearch?publisher=7386450885104269&q=java&l=panama+city%2C+fl&sort=&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
    webView = new WebView(context);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadUrl(url);
    
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    layoutView.addView(webView);
    attached = true;
  }

  @Override
  protected void detachView() {
    layoutView.removeView(webView);
    attached = false;
  }

  @Override
  protected void doRibbonAction() {
    
  }

  @Override
  public void onResume() {
    webView.onResume();
  }

  @Override
  public void onPause() {
    webView.onPause();
  }

  @Override
  public void onDestroy() {
    
  }

  @Override
  public void onLowMemory() {
    
  }
}