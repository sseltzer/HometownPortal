package com.example.iconribbontest;

import com.dylf.hometown.IconRibbon;
import com.dylf.hometown.RibbonItem;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends Activity {
  private View.OnClickListener listener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      actionFunnel(v);
    }
  };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    IconRibbon ribbon = new IconRibbon(this.getBaseContext(), null);
    for(RibbonElements element : RibbonElements.values()) ribbon.addItem(element.getItem(listener));
    addContentView(ribbon, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  public void actionFunnel(View v) {
    Log.d("debug", "View pressed: " + v.getId());
    if      (v.getId() == RibbonElements.ENTERTAINMENT.getID()) Log.d("debug", "entertainment");
    else if (v.getId() == RibbonElements.FOOD.getID()) Log.d("debug", "food");
    else if (v.getId() == RibbonElements.SHOPPING.getID()) Log.d("debug", "shopping");
    else if (v.getId() == RibbonElements.EMPLOYMENT.getID()) Log.d("debug", "employment");
    else if (v.getId() == RibbonElements.SCHOOLS.getID()) Log.d("debug", "schools");
    else if (v.getId() == RibbonElements.MUSEUMS.getID()) Log.d("debug", "museums");
    else if (v.getId() == RibbonElements.NIGHTLIFE.getID()) Log.d("debug", "nightlife");
    else if (v.getId() == RibbonElements.WEATHER.getID()) Log.d("debug", "weather");
    else if (v.getId() == RibbonElements.EVENTS.getID()) Log.d("debug", "events");
    else if (v.getId() == RibbonElements.MAPS.getID()) Log.d("debug", "maps");
  }
  
  public enum RibbonElements {
    ENTERTAINMENT(new RibbonItem(R.id.enterainmentID, R.drawable.entertainment_d, R.drawable.entertainment_u, null)),
    FOOD         (new RibbonItem(R.id.foodID,         R.drawable.food_d,          R.drawable.food_u,          null)),
    SHOPPING     (new RibbonItem(R.id.shoppingID,     R.drawable.shopping_d,      R.drawable.shopping_u,      null)),
    EMPLOYMENT   (new RibbonItem(R.id.employmentID,   R.drawable.employment_d,    R.drawable.employment_u,    null)),
    SCHOOLS      (new RibbonItem(R.id.schoolsID,      R.drawable.schools_d,       R.drawable.schools_u,       null)),
    MUSEUMS      (new RibbonItem(R.id.museumsID,      R.drawable.museums_d,       R.drawable.museums_u,       null)),
    NIGHTLIFE    (new RibbonItem(R.id.nightlifeID,    R.drawable.nightlife_d,     R.drawable.nightlife_u,     null)),
    WEATHER      (new RibbonItem(R.id.weatherID,      R.drawable.weather_d,       R.drawable.weather_u,       null)),
    EVENTS       (new RibbonItem(R.id.eventsID,       R.drawable.events_d,        R.drawable.events_u,        null)),
    MAPS         (new RibbonItem(R.id.mapsID,         R.drawable.maps_d,          R.drawable.maps_u,          null));
    private RibbonItem item; 
    private RibbonElements(RibbonItem item) {
      this.item = item;
    }
    public RibbonItem getItem(OnClickListener listener) {
      item.setListener(listener);
      return item;
    }
    public int getID() {
      return item.getBID();
    }
  }
}
