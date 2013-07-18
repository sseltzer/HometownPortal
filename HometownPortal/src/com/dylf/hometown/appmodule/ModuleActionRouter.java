package com.dylf.hometown.appmodule;

import android.util.SparseArray;
import android.view.View;

public class ModuleActionRouter {
  
  private SparseArray<AppModule> callbackMap;
  private AppModule loadedModule;
  
  public ModuleActionRouter() {
    callbackMap = new SparseArray<AppModule>();
  }
  
  private View.OnClickListener listener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      actionFunnel(v);
    }
  };
  
  public View.OnClickListener getListener() {
    return listener;
  }
  
  public void actionFunnel(View v) {
    AppModule callbackModule = callbackMap.get(v.getId()); 
    if (callbackModule == null) return;
    if (loadedModule != null && loadedModule == callbackModule) return;
    if (loadedModule != null) loadedModule.detachView();
    callbackModule.attachView();
    loadedModule = callbackModule;
    callbackModule.doRibbonAction();
  }
  public void addCallback(int bid, AppModule module) {
    callbackMap.put(bid, module);
  }
  
  public AppModule getLoadedModule() {
    return loadedModule;
  }
}
