package com.dylf.hometown.appmodule;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dylf.hometown.RibbonItem;

public abstract class AppModule {
  private RibbonItem ribbonItem;
  protected LinearLayout layoutView;
  
  public AppModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    this.layoutView = layoutView;
  }
  public void generateRibbonItem(ModuleConfigs config, View.OnClickListener listener) {
    ribbonItem = new RibbonItem(config.getBID(), config.getDID(), config.getUID(), listener);
  }
  public RibbonItem getRibbonItem() {
    return ribbonItem;
  }
  
  protected abstract void generateView(Context context, Bundle savedInstanceState);
  protected abstract void attachView();
  protected abstract void detachView();
  protected abstract void doRibbonAction();
  
  public abstract void onResume();
  public abstract void onPause();
  public abstract void onDestroy();
  public abstract void onLowMemory();
}
