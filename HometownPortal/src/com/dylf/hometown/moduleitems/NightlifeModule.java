package com.dylf.hometown.moduleitems;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.mapmanager.ModuleLocationManager;
import com.dylf.hometown.moduleitems.mapmanager.ModuleMapManager;
import com.dylf.hometown.moduleitems.mapmanager.ModulePlacesManager;
import com.dylf.hometown.moduleitems.mapmanager.ModulePlacesManager.QueryType;

public class NightlifeModule extends AppModule {
  boolean attached;
  
  private ModuleMapManager mMm;
  private ModuleLocationManager mLm;
  private ModulePlacesManager mPm;
 
  public NightlifeModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.NIGHTLIFE, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
    
    mMm = ModuleMapManager.requestInstance(context, savedInstanceState);
    mLm = ModuleLocationManager.requestInstance(context, savedInstanceState);
    mPm = ModulePlacesManager.requestInstance(context, savedInstanceState);
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
    try {
      mMm.markUp(mPm.query(QueryType.NIGHTLIFE, mLm.getLatLng()));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
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
