package com.dylf.hometown;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {

  private MapView mapView;
  private LinearLayout contentLayout;

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
    contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

    IconRibbon ribbon = new IconRibbon(this.getBaseContext(), null);
    for (RibbonElements element : RibbonElements.values()) ribbon.addItem(element.getItem(listener));
    addContentView(ribbon, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    GoogleMapOptions options = new GoogleMapOptions();
    options.camera(new CameraPosition(new LatLng(30.1586, -85.6603), 11, 0, 0));
    options.compassEnabled(true);
    mapView = new MapView(this.getBaseContext(), options);
    mapView.onCreate(savedInstanceState);
    contentLayout.addView(mapView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapView.onResume();

  }

  @Override
  protected void onPause() {
    mapView.onPause();
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    mapView.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  public void actionFunnel(View v) {
    Log.d("debug", "View pressed: " + v.getId());
    if (v.getId() == RibbonElements.ENTERTAINMENT.getID()) Log.d("debug", "entertainment");
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
    FOOD(new RibbonItem(R.id.foodID, R.drawable.food_d, R.drawable.food_u, null)),
    SHOPPING(new RibbonItem(R.id.shoppingID, R.drawable.shopping_d, R.drawable.shopping_u, null)),
    EMPLOYMENT(new RibbonItem(R.id.employmentID, R.drawable.employment_d, R.drawable.employment_u,null)), 
    SCHOOLS(new RibbonItem(R.id.schoolsID, R.drawable.schools_d, R.drawable.schools_u, null)), 
    MUSEUMS(new RibbonItem(R.id.museumsID, R.drawable.museums_d, R.drawable.museums_u, null)), 
    NIGHTLIFE(new RibbonItem(R.id.nightlifeID, R.drawable.nightlife_d, R.drawable.nightlife_u, null)), 
    WEATHER(new RibbonItem(R.id.weatherID, R.drawable.weather_d, R.drawable.weather_u, null)), 
    EVENTS(new RibbonItem(R.id.eventsID, R.drawable.events_d, R.drawable.events_u,null)), 
    MAPS(new RibbonItem(R.id.mapsID, R.drawable.maps_d, R.drawable.maps_u, null));
    
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

/*
C:\Program Files\Java\jdk1.7.0_25\bin>keytool -list -v -keystore "C:\Users\naman
s\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass an
droid
Alias name: androiddebugkey
Creation date: Jul 4, 2013
Entry type: PrivateKeyEntry
Certificate chain length: 1
Certificate[1]:
Owner: CN=Android Debug, O=Android, C=US
Issuer: CN=Android Debug, O=Android, C=US
Serial number: 66173f99
Valid from: Thu Jul 04 00:04:28 EDT 2013 until: Sat Jun 27 00:04:28 EDT 2043
Certificate fingerprints:
         MD5:  EE:00:61:4B:2C:78:A1:51:A6:BF:3D:15:CA:C2:15:CB
         SHA1: EB:5F:BD:82:9A:81:2C:F6:61:E3:93:FC:E9:F1:1B:31:2B:BB:3C:CF
         SHA256: B5:63:7E:03:2D:08:CE:53:A7:E5:2A:6E:76:4B:1E:54:E6:4D:2E:53:1E:
A6:6D:01:8C:CE:64:BE:04:83:85:03
         Signature algorithm name: SHA256withRSA
         Version: 3

Extensions:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 73 62 32 4C 81 35 0E 58   51 03 C1 39 1C BD 9D B0  sb2L.5.XQ..9....
0010: 49 E5 72 77                                        I.rw
]
]


C:\Program Files\Java\jdk1.7.0_25\bin>
 */
