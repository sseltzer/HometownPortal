package com.dylf.hometown.moduleitems.mapmanager;

import com.google.android.gms.maps.model.LatLng;

public class Place {

  private String nameStr;
  private LatLng latLng;
  
  public Place(String nameStr, LatLng latLng) {
    this.nameStr = nameStr;
    this.latLng = latLng;
  }
  
  public String getNameStr() {
    return nameStr;
  }
  public LatLng getLatLng() {
    return latLng;
  }

}
