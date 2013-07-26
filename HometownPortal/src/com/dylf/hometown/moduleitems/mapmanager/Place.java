package com.dylf.hometown.moduleitems.mapmanager;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Place implements Parcelable {

  private String nameStr;
  private String addressStr;
  private LatLng latLng;

  public Place(String nameStr, String addressStr, LatLng latLng) {
    this.nameStr = nameStr;
    this.addressStr = addressStr;
    this.latLng = latLng;
  }
  private Place(Parcel in) {
    nameStr = in.readString();
    addressStr = in.readString();
    latLng = in.readParcelable(LatLng.class.getClassLoader());
  }

  public String getNameStr() {
    return nameStr;
  }
  public void setNameStr(String addressStr) {
    this.addressStr = addressStr;
  }
  
  public String getAddressStr() {
    return addressStr;
  }
  public void setAddressStr(String addressStr) {
    this.addressStr = addressStr;
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
    dest.writeString(addressStr);
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
