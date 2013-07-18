package com.dylf.hometown;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.moduleitems.FoodModule;
import com.dylf.hometown.moduleitems.MapModule;

public class MainActivity extends FragmentActivity {

  private ArrayList<AppModule> modules;
  private LinearLayout contentLayout;
  private ModuleActionRouter router;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

    router = new ModuleActionRouter();
    
    modules = new ArrayList<AppModule>();
    modules.add(new FoodModule(contentLayout, this, savedInstanceState, router));
    modules.add(new MapModule(contentLayout, this, savedInstanceState, router));
    
    IconRibbon ribbon = new IconRibbon(this.getBaseContext(), null);
    for (AppModule module : modules) ribbon.addItem(module.getRibbonItem());
    addContentView(ribbon, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  protected void onResume() {
    super.onResume();
    for (AppModule module : modules) module.onResume();
  }
  @Override
  protected void onPause() {
    for (AppModule module : modules) module.onPause();
    super.onPause();
  }
  @Override
  protected void onDestroy() {
    for (AppModule module : modules) module.onDestroy();
    super.onDestroy();
  }
  @Override
  public void onLowMemory() {
    super.onLowMemory();
    for (AppModule module : modules) module.onLowMemory();
  }

}
