package com.dylf.hometown.moduleitems.mapmanager;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Place implements Parcelable {

  private String nameStr;
  private LatLng latLng;

  public Place(String nameStr, LatLng latLng) {
    this.nameStr = nameStr;
    this.latLng = latLng;
  }
  private Place(Parcel in) {
    nameStr = in.readString();
    latLng = in.readParcelable(LatLng.class.getClassLoader());
  }

  public String getNameStr() {
    return nameStr;
  }
  public void setNameStr(String nameStr) {
    this.nameStr = nameStr;
  }

  public LatLng getLatLng() {
    return latLng;
  }
  public void setLatLng(LatLng latLng) {
    this.latLng = latLng;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(nameStr);
    dest.writeParcelable(latLng, 0);
  }

  public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
    public Place createFromParcel(Parcel in) {
      return new Place(in);
    }

    @Override
    public Place[] newArray(int size) {
      return new Place[size];
    }
  };

}
