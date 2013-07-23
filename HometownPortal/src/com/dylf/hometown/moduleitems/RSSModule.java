package com.dylf.hometown.moduleitems;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dylf.hometown.R;
import com.dylf.hometown.appmodule.AppModule;
import com.dylf.hometown.appmodule.ModuleActionRouter;
import com.dylf.hometown.appmodule.ModuleConfigs;
import com.dylf.hometown.moduleitems.MapManager.GetPlace;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class RSSModule extends AppModule implements OnItemSelectedListener {

  private final String MAPKEY;
  private final String PLACESKEY;
  
  boolean attached;
  LinearLayout layout;
  MapView mapView;
  LocationManager locationManager;
  Location location;
  Spinner spinner;
  
  String normal;
  String hybrid;
  String satellite;
  
  public RSSModule(LinearLayout layoutView, Context context, Bundle savedInstanceState, ModuleActionRouter router) {
    super(layoutView, context, savedInstanceState, router);
    MAPKEY = context.getString(R.string.mapkey);
    PLACESKEY = context.getString(R.string.placeskey);
    attached = false;
    generateRibbonItem(ModuleConfigs.MAPS, router.getListener()); //keep but needs to be changed for RSS
    
    //locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    
    generateView(context, savedInstanceState); //keep
    router.addCallback(getRibbonItem().getBID(), this); //keep
  }

  @Override
  protected void generateView(Context context, Bundle savedInstanceState) {
    layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    spinner = new Spinner(context);
    //Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.layers_array, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    
    normal = context.getString(R.string.normal);
    hybrid = context.getString(R.string.hybrid);
    satellite = context.getString(R.string.satellite);
    
    GoogleMapOptions options = new GoogleMapOptions();
    //options.camera(new CameraPosition(new LatLng(30.1586, -85.6603), 11, 0, 0));
    options.camera(new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 11, 0, 0));
    mapView = new MapView(context, options);
    mapView.onCreate(savedInstanceState);
    mapView.getMap().setMyLocationEnabled(true);
    
    mapView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    spinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.addView(spinner);
    layout.addView(mapView);
  }

  @Override
  protected void attachView() {
    if (attached || layoutView == null) return;
    layoutView.addView(layout);
    attached = true;
  }

  @Override
  protected void detachView() {
    layoutView.removeView(layout);
    attached = false;
  }

  @Override
  protected void doRibbonAction() {
    
  }

  @Override
  public void onResume() {
    mapView.onResume();
  }

  @Override
  public void onPause() {
    mapView.onPause();
  }

  @Override
  public void onDestroy() {
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    mapView.onLowMemory();
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    setLayer((String) parent.getItemAtPosition(position));
  }
  private void setLayer(String layerName) {
    if      (layerName.equals(normal))    mapView.getMap().setMapType(MAP_TYPE_NORMAL);
    else if (layerName.equals(hybrid))    mapView.getMap().setMapType(MAP_TYPE_HYBRID);
    else if (layerName.equals(satellite)) mapView.getMap().setMapType(MAP_TYPE_SATELLITE);
    /*
    String query = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                   "json?location=" + location.getLatitude() + "," + location.getLongitude() +
                   "&radius=1000&sensor=true" +
                   "&types=food"+
                   "&key=" + APIKEY;
     */
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&sensor=false&key=AddYourOwnKeyHere
    String query = "https://maps.googleapis.com/maps/api/place/search/json?" + 
                   "location=" + location.getLatitude() + "," + location.getLongitude() +
                   "&radius=2000" + 
                   "&types=restaurant" +
                   "&sensor=true" +
                   "&key=" + PLACESKEY;
    //AIzaSyANzKHdNlv5c8fvD7xBMohBZPQz15u2CIY";
    Log.d("debug", "Query: " + query);
    GetPlace getPlace = new GetPlace();
    AsyncTask<String, Void, String> sb = getPlace.execute(query);
    try {
      Log.d("debug", "Result: " + sb.get());
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
}

  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
  }
}
