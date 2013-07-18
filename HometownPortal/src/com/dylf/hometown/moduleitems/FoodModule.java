package com.dylf.hometown.moduleitems;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;

public class FoodModule extends AppModule {
  boolean attached;

  public FoodModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.FOOD, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    attached = true;
  }

  @Override
  protected void detachView() {
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
