package com.dylf.hometown.moduleitems;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapModule extends AppModule {

  boolean attached;
  MapView mapView;
  
  public MapModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.MAPS, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    GoogleMapOptions options = new GoogleMapOptions();
    options.camera(new CameraPosition(new LatLng(30.1586, -85.6603), 11, 0, 0));
    options.compassEnabled(true);
    mapView = new MapView(context, options);
    mapView.onCreate(savedInstanceState);
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    layoutView.addView(mapView);
    attached = true;
  }

  @Override
  protected void detachView() {
    layoutView.removeView(mapView);
    attached = false;
  }

  @Override
  protected void doRibbonAction() {
    
  }

  @Override
  public void onResume() {
    mapView.onResume();
  }

  @Override
  public void onPause() {
    mapView.onPause();
  }

  @Override
  public void onDestroy() {
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    mapView.onLowMemory();
  }
}
