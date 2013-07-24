package com.dylf.hometown.moduleitems.mapmanager;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dylf.hometown.R;
import com.google.android.gms.maps.model.LatLng;

public class ModulePlacesManager {
  
  private static ModulePlacesManager instance = null;
  
  private final String PLACESKEY;
  private EnumMap<QueryType, ArrayList<Place>> queryMap;
  
  private ModulePlacesManager(Context context, Bundle savedInstanceState) {
    PLACESKEY = context.getString(R.string.placeskey);
     
    queryMap = new EnumMap<QueryType, ArrayList<Place>>(QueryType.class);
  }
  
  public ArrayList<Place> query(QueryType queryType, LatLng location) throws InterruptedException, ExecutionException {
    if (queryMap.containsKey(queryType)) return queryMap.get(queryType);
    PlacesRetriever placesRetriever = new PlacesRetriever();
    AsyncTask<String, Void, String> task = placesRetriever.execute(buildQuery(queryType, location));
    String retStr = task.get();
    ArrayList<Place> queryList = parseResonse(retStr);
    queryMap.put(queryType, queryList);
    return queryList;
  }
  
  public static ModulePlacesManager requestInstance(Context context, Bundle savedInstanceState) {
    if (instance == null) instance = new ModulePlacesManager(context, savedInstanceState);
    return instance;
  }
  
  private String buildQuery(QueryType queryType, LatLng location) {
    return "https://maps.googleapis.com/maps/api/place/search/json?" + 
        "location=" + location.latitude + "," + location.longitude +
        "&radius=2000" + 
        "&types=" + queryType.getQueryStr() +
        "&sensor=true" +
        "&key=" + PLACESKEY;
  }
  private ArrayList<Place> parseResonse(String json) {
    ArrayList<Place> placeList = new ArrayList<Place>();
    
    try {
      JSONObject entries = new JSONObject(json);
      JSONArray results = entries.getJSONArray("results");
      for (int i = 0; i < results.length(); ++i) {
        JSONObject name = results.getJSONObject(i);
        JSONObject geom = (JSONObject) name.get("geometry");
        JSONObject loc = (JSONObject) geom.get("location");
        
        String nameStr = name.get("name").toString();
        double lat = Double.parseDouble(loc.get("lat").toString());
        double lng = Double.parseDouble(loc.get("lng").toString());
        
        Place place = new Place(nameStr, new LatLng(lat, lng));
        placeList.add(place);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return placeList;
  }
  
  public enum QueryType {
    FOOD("restaurant"),
    SHOPPING("department_store"),
    SCHOOLS("school"),
    MUSEUMS("museums"),
    NIGHTLIFE("bar|night_club");
    private String queryStr;
    private QueryType(String queryStr) {
      this.queryStr = queryStr;
    }
    public String getQueryStr() {
      return queryStr;
    }
  }
}
