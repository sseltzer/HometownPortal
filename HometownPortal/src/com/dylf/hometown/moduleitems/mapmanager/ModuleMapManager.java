package com.dylf.hometown.moduleitems.mapmanager;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dylf.hometown.R;
import com.dylf.hometown.moduleitems.mapmanager.ModulePlacesManager.QueryType;
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
  private Spinner layerSpinner;
  private Spinner distanceSpinner;
  private Button searchButton;
  private QueryType lastQuery;
  private ModulePlacesManager mPm;
  private ModuleLocationManager mLm;
  //private ArrayList<Place> places;
  
  
  private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      if (parent.equals(layerSpinner)) {
        setLayer(MapMode.getMapModeFromString((String) parent.getItemAtPosition(position)));
        return;
      }
      if (parent.equals(distanceSpinner)) doSearch();
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
  };
  private OnClickListener onClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      doSearch();
    }
  };
  
  private ModuleMapManager(Context context, Bundle savedInstanceState) {
    mPm = ModulePlacesManager.requestInstance(context, savedInstanceState);
    mLm = ModuleLocationManager.requestInstance(context, savedInstanceState);
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
    LinearLayout linearLayout = new LinearLayout(context);
    
    layerSpinner = new Spinner(context);
    //Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.layers_array, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    layerSpinner.setAdapter(adapter);
    layerSpinner.setOnItemSelectedListener(onItemSelectedListener);

    ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(context, R.array.distance_array, android.R.layout.simple_spinner_item);
    distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    distanceSpinner = new Spinner(context);
    distanceSpinner.setAdapter(distanceAdapter);
    distanceSpinner.setOnItemSelectedListener(onItemSelectedListener);
    
    searchButton = new Button(context);
    searchButton.setText(R.string.searchString);
    searchButton.setOnClickListener(onClickListener);
    
    linearLayout.addView(distanceSpinner, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    linearLayout.addView(layerSpinner, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    linearLayout.addView(searchButton, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    
    layout.addView(linearLayout);
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
  
  public void markUp(QueryType queryType, ArrayList<Place> places) {
    if (places == null) return;
    lastQuery = queryType;
    //this.places = places;
    for(Place place : places) {
      //Log.d("debug", "Place: " + place.getNameStr() + " Address: " + place.getAddressStr() + " LatLng: " + place.getLatLng());
      MarkerOptions options = new MarkerOptions();
      options.title(place.getNameStr());
      options.snippet(place.getAddressStr());
      options.position(place.getLatLng());
      mapView.getMap().addMarker(options);
    }
  }
  public void doSearch() {
    int distance = mPm.stringMilesToMeters((String) distanceSpinner.getSelectedItem());
    mapView.getMap().clear();
    try {
      markUp(lastQuery, mPm.query(lastQuery, mLm.getLatLng(), distance));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
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
    mapView.getMap().setInfoWindowAdapter(new PlaceInfo(context));
    setLayer(MapMode.getMapModeFromString((String) layerSpinner.getSelectedItem()));
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
