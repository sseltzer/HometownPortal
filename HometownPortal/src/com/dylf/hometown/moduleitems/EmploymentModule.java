package com.dylf.hometown.moduleitems;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.employment.EmploymentDisplayManager;


public class EmploymentModule extends AppModule {
  EmploymentDisplayManager EDM;
  boolean attached;
  
  
  public EmploymentModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    attached = false;
    generateRibbonItem(ModuleConfigs.EMPLOYMENT, router.getListener());
    generateView(context, savedInstanceState);
    router.addCallback(getRibbonItem().getBID(), this);
    EDM = new EmploymentDisplayManager(context, savedInstanceState);
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    	
  }

  @Override
  protected void attachView() {
	  if (attached || layoutView == null) return;
	    attached = true;
	    EDM.requestAttach(layoutView);
  }

  @Override
  protected void detachView() {
	  EDM.requestDetach(layoutView);
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