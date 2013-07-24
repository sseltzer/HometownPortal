package com.dylf.hometown.moduleitems.mapmanager;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import java.util.ArrayList;

import android.content.Context;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ModuleMapManager {
  
  private static ModuleMapManager instance = null;
  //private final String MAPSKEY;
  
  private LinearLayout currentAnchor;
  private LinearLayout layout;
  private MapView mapView;
  private Spinner spinner;
  //private ArrayList<Place> places;
  
  
  private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      setLayer(MapMode.getMapModeFromString((String) parent.getItemAtPosition(position)));
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
  };
  
  private ModuleMapManager(Context context, Bundle savedInstanceState) {
    try {
      MapsInitializer.initialize(context);
    } catch (GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
    
    //MAPSKEY = context.getString(R.string.mapkey);
    
    MapMode.NORMAL.setModeStr(context.getString(R.string.normal));
    MapMode.HYBRID.setModeStr(context.getString(R.string.hybrid));
    MapMode.SATELLITE.setModeStr(context.getString(R.string.satellite));
    
    generateView(context, savedInstanceState);
  }
  public static ModuleMapManager requestInstance(Context context, Bundle savedInstanceState) {
    if (instance == null) instance = new ModuleMapManager(context, savedInstanceState);
    return instance;
  }
  
  private void generateView(Context context, Bundle savedInstanceState) {
    layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    spinner = new Spinner(context);
    //Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.layers_array, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(onItemSelectedListener);
    spinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.addView(spinner);
  }
  
  public void requestAttach(LinearLayout anchor) {
    if (currentAnchor != null || anchor == null) return;
    currentAnchor = anchor;
    anchor.addView(layout);
  }
  public void requestDetach(LinearLayout anchor) {
    if (currentAnchor == null || !currentAnchor.equals(anchor)) return;
    currentAnchor.removeView(layout);
    currentAnchor = null;
    mapView.getMap().clear();
  }
  
  private void setLayer(MapMode mapMode) {
    switch(mapMode) {
      case NORMAL: mapView.getMap().setMapType(MAP_TYPE_NORMAL); break;
      case HYBRID: mapView.getMap().setMapType(MAP_TYPE_HYBRID); break;
      case SATELLITE: mapView.getMap().setMapType(MAP_TYPE_SATELLITE); break;
    }
  }
  public void animateNewLatLng(LatLng latLng) {
    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(11).build();
    mapView.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
  }
  
  public void markUp(ArrayList<Place> places) {
    if (places == null) return;
    //this.places = places;
    for(Place place : places) {
      Log.d("debug", "Place: " + place.getNameStr() + " LatLng: " + place.getLatLng());
      MarkerOptions options = new MarkerOptions();
      options.title(place.getNameStr());
      options.position(place.getLatLng());
      mapView.getMap().addMarker(options);
    }
  }
  public void onCreate(Context context, Bundle savedInstanceState) {
    try {
      MapsInitializer.initialize(context);
    } catch (GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
    /*
    if (savedInstanceState != null) {
      places = savedInstanceState.getParcelableArrayList("places");
      savedInstanceState.remove("places");
    }*/
    mapView = new MapView(context);
    mapView.onCreate(savedInstanceState);
    mapView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.addView(mapView);
    mapView.getMap().setMyLocationEnabled(true);
    setLayer(MapMode.getMapModeFromString((String) spinner.getSelectedItem()));
    //markUp(places);
  }
  public void onResume() {
    mapView.onResume();
  }

  public void onPause() {
    mapView.onPause();
  }

  public void onDestroy() {
    requestDetach(currentAnchor);
    mapView.onDestroy();
    layout.removeView(mapView);
    mapView = null;
  }

  public void onLowMemory() {
    mapView.onLowMemory();
  }
  public void onSaveInstanceState(Bundle outState) {
    mapView.onSaveInstanceState(outState);
    //outState.putParcelableArrayList("places", places);
  }
  
  public enum MapMode {
    NORMAL,
    HYBRID,
    SATELLITE;
    private String modeStr = null;
    public void setModeStr(String modeStr) {
      this.modeStr = modeStr;
    }
    public String getModeStr() {
      return modeStr;
    }
    public static MapMode getMapModeFromString(String modeStr) {
      for(MapMode mode : MapMode.values()) if (mode.getModeStr().equals(modeStr)) return mode;
      return NORMAL;
    }
  }
}
