package com.dylf.hometown;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.moduleitems.FoodModule;
import com.dylf.hometown.moduleitems.MapModule;
import com.dylf.hometown.moduleitems.MuseumsModule;
import com.dylf.hometown.moduleitems.NightlifeModule;
import com.dylf.hometown.moduleitems.RSSModule;
import com.dylf.hometown.moduleitems.SchoolsModule;
import com.dylf.hometown.moduleitems.ShoppingModule;
import com.dylf.hometown.moduleitems.mapmanager.ModuleMapManager;

public class MainActivity extends FragmentActivity {

  private ArrayList<AppModule> modules;
  private LinearLayout contentLayout;
  private ModuleActionRouter router;
  
  private ModuleMapManager mMm;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

    mMm = ModuleMapManager.requestInstance(this, savedInstanceState);
    mMm.onCreate(this, savedInstanceState);
    router = new ModuleActionRouter();
    
    modules = new ArrayList<AppModule>();
    modules.add(new FoodModule(contentLayout, this, savedInstanceState, router));
    modules.add(new ShoppingModule(contentLayout, this, savedInstanceState, router));
    modules.add(new SchoolsModule(contentLayout, this, savedInstanceState, router));
    modules.add(new MuseumsModule(contentLayout, this, savedInstanceState, router));
    modules.add(new NightlifeModule(contentLayout, this, savedInstanceState, router));
    modules.add(new RSSModule(contentLayout, this, savedInstanceState, router));
    modules.add(new MapModule(contentLayout, this, savedInstanceState, router));
    
    IconRibbon ribbon = new IconRibbon(this.getBaseContext(), null);
    for (AppModule module : modules) ribbon.addItem(module.getRibbonItem());
    // Removed this to implement fix for attach overlay. Else the module will overlap the ribbon.
    //addContentView(ribbon, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    LinearLayout ribbonLayout = (LinearLayout)findViewById(R.id.ribbonLayout);
    ribbonLayout.addView(ribbon);
    router.actionFunnel(findViewById(modules.get(0).getRibbonItem().getBID()));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  protected void onResume() {
    
    mMm.onResume();
    //for (AppModule module : modules) module.onResume();
    super.onResume();
  }
  @Override
  protected void onPause() {
    mMm.onPause();
    //for (AppModule module : modules) module.onPause();
    super.onPause();
  }
  @Override
  protected void onDestroy() {
    mMm.onDestroy();
    //for (AppModule module : modules) module.onDestroy();
    super.onDestroy();
  }
  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mMm.onLowMemory();
    //for (AppModule module : modules) module.onLowMemory();
  }
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mMm.onSaveInstanceState(outState);
  }
}
