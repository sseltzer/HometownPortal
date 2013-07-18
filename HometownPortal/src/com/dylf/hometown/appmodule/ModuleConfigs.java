package com.dylf.hometown.appmodule;

import com.dylf.hometown.R;

public enum ModuleConfigs {
  ENTERTAINMENT(R.id.enterainmentID, R.drawable.entertainment_d, R.drawable.entertainment_u), 
  FOOD         (R.id.foodID,         R.drawable.food_d,          R.drawable.food_u),
  SHOPPING     (R.id.shoppingID,     R.drawable.shopping_d,      R.drawable.shopping_u),
  EMPLOYMENT   (R.id.employmentID,   R.drawable.employment_d,    R.drawable.employment_u), 
  SCHOOLS      (R.id.schoolsID,      R.drawable.schools_d,       R.drawable.schools_u),
  MUSEUMS      (R.id.museumsID,      R.drawable.museums_d,       R.drawable.museums_u), 
  NIGHTLIFE    (R.id.nightlifeID,    R.drawable.nightlife_d,     R.drawable.nightlife_u), 
  WEATHER      (R.id.weatherID,      R.drawable.weather_d,       R.drawable.weather_u),
  EVENTS       (R.id.eventsID,       R.drawable.events_d,        R.drawable.events_u),
  MAPS         (R.id.mapsID,         R.drawable.maps_d,          R.drawable.maps_u);
  
  private int bid;
  private int did;
  private int uid;
  
  private ModuleConfigs(int bid, int did, int uid) {
    this.bid = bid;
    this.did = did;
    this.uid = uid;
  }
  
  public int getBID() {
    return bid;
  }
  public int getDID() {
    return did;
  }
  public int getUID() {
    return uid;
  }
}
