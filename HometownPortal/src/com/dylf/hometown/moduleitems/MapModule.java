package com.dylf.hometown.moduleitems;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.mapmanager.ModuleLocationManager;
import com.dylf.hometown.moduleitems.mapmanager.ModuleMapManager;

public class MapModule extends AppModule {
  boolean attached;
  
  private ModuleMapManager mMm;
  private ModuleLocationManager mLm;
  //private ModulePlacesManager mPm;
 
  public MapModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.MAPS, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
    
    mMm = ModuleMapManager.requestInstance(context, savedInstanceState);
    mLm = ModuleLocationManager.requestInstance(context, savedInstanceState);
    //mPm = ModulePlacesManager.requestInstance(context, savedInstanceState);
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    // Maps are a special case handled by ModuleMapManager since all maps use a common view.
    // This area could be used for overlays and additions.
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    attached = true;
    mMm.requestAttach(layoutView);
  }

  @Override
  protected void detachView() {
    mMm.requestDetach(layoutView);
    attached = false;
  }

  @Override
  protected void doRibbonAction() {
    mMm.animateNewLatLng(mLm.getLatLng());
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
