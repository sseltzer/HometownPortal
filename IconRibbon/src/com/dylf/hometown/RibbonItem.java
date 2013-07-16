package com.dylf.hometown;

import android.view.View.OnClickListener;

public class RibbonItem {

  private int bID;
  private int dID;
  private int uID;
  private OnClickListener listener;
  
  public int getBID() {
    return bID;
  }
  public void setBID(int bID) {
    this.bID = bID;
  }
  public int getDID() {
    return dID;
  }
  public void setDID(int dID) {
    this.dID = dID;
  }
  public int getUID() {
    return uID;
  }
  public void setUID(int uID) {
    this.uID = uID;
  }
  public OnClickListener getListener() {
    return listener;
  }
  public void setListener(OnClickListener listener) {
    this.listener = listener;
  }
  
  public RibbonItem(int bID, int dID, int uID, OnClickListener listener) {
    this.bID = bID;
    this.dID = dID;
    this.uID = uID;
    this.listener = listener;
  }
}
