package com.dylf.hometown;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.moduleitems.EmploymentModule;
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
  private RelativeLayout splashLayout;
  private LinearLayout ribbonLayout;
  private LinearLayout contentLayout;
  private ModuleActionRouter router;
  
  private ModuleMapManager mMm;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    splashLayout =  (RelativeLayout) findViewById(R.id.splashLayout);
    contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

    mMm = ModuleMapManager.requestInstance(this, savedInstanceState);
    mMm.onCreate(this, savedInstanceState);
    router = new ModuleActionRouter();
    
    modules = new ArrayList<AppModule>();
    modules.add(new FoodModule(contentLayout, this, savedInstanceState, router));
    modules.add(new ShoppingModule(contentLayout, this, savedInstanceState, router));
    modules.add(new EmploymentModule(contentLayout, this, savedInstanceState, router));
    modules.add(new SchoolsModule(contentLayout, this, savedInstanceState, router));
    modules.add(new MuseumsModule(contentLayout, this, savedInstanceState, router));
    modules.add(new NightlifeModule(contentLayout, this, savedInstanceState, router));
    modules.add(new RSSModule(contentLayout, this, savedInstanceState, router));
    modules.add(new MapModule(contentLayout, this, savedInstanceState, router));
    
    IconRibbon ribbon = new IconRibbon(this.getBaseContext(), null);
    for (AppModule module : modules) ribbon.addItem(module.getRibbonItem());
    // Removed this to implement fix for attach overlay. Else the module will overlap the ribbon.
    //addContentView(ribbon, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    ribbonLayout = (LinearLayout)findViewById(R.id.ribbonLayout);
    ribbonLayout.addView(ribbon);
    router.actionFunnel(findViewById(modules.get(0).getRibbonItem().getBID()));
    
    Handler handler = new Handler();
    handler.postDelayed(removeSplash(), 3000);
  }
  
  public Runnable removeSplash() {
    return new Runnable() {
      @Override
      public void run() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(600);
        fadeIn.setFillAfter(true);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(400);
        fadeOut.setFillAfter(true);
        splashLayout.startAnimation(fadeOut);
        ribbonLayout.startAnimation(fadeIn);
        contentLayout.startAnimation(fadeIn);
      }
    };
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
