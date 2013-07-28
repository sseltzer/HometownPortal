package com.dylf.hometown.moduleitems.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.dylf.hometown.moduleitems.weather.WeatherViewManager.OutlookObject;

public class OutlookRetriever extends AsyncTask<String, Void, JSONObject> {
  private JSONObject data;
  private String[] dayArray = new String[4];
  private String[] iconUrlArray = new String[4];
  private String[] descArray = new String[4];
  
  private WeatherViewManager wVm;
  
  public OutlookRetriever(WeatherViewManager wVm) {
    this.wVm = wVm;
  }

  protected JSONObject doInBackground(String... url) {
    JSONParser parse = new JSONParser();
    data = parse.getJSONFromUrl(url[0]);
    return data;
  }
  protected void onPostExecute(JSONObject data) {
    if (wVm == null) return;
    try {
      JSONObject layer1 = data.getJSONObject("forecast");
      JSONObject layer2 = layer1.getJSONObject("txt_forecast");
      JSONArray outArray = layer2.getJSONArray("forecastday");
      for (int x = 0; x < 4; ++x) {
        JSONObject focus = outArray.getJSONObject(x);
        dayArray[x] = focus.getString("title");
        iconUrlArray[x] = focus.getString("icon_url");
        descArray[x] = focus.getString("fcttext");
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    new ImageRetriever((ImageView)wVm.getOutlookViewItem(OutlookObject.ICON1)).execute(iconUrlArray[0]);
    new ImageRetriever((ImageView)wVm.getOutlookViewItem(OutlookObject.ICON2)).execute(iconUrlArray[0]);
    new ImageRetriever((ImageView)wVm.getOutlookViewItem(OutlookObject.ICON3)).execute(iconUrlArray[0]);
    new ImageRetriever((ImageView)wVm.getOutlookViewItem(OutlookObject.ICON4)).execute(iconUrlArray[0]);
    
    ((TextView)wVm.getOutlookViewItem(OutlookObject.HEAD1)).setText(dayArray[0]);
    ((TextView)wVm.getOutlookViewItem(OutlookObject.HEAD2)).setText(dayArray[1]);
    ((TextView)wVm.getOutlookViewItem(OutlookObject.HEAD3)).setText(dayArray[2]);
    ((TextView)wVm.getOutlookViewItem(OutlookObject.HEAD4)).setText(dayArray[3]);
    
    ((TextView)wVm.getOutlookViewItem(OutlookObject.DESC1)).setText(descArray[0]);
    ((TextView)wVm.getOutlookViewItem(OutlookObject.DESC2)).setText(descArray[1]);
    ((TextView)wVm.getOutlookViewItem(OutlookObject.DESC3)).setText(descArray[2]);
    ((TextView)wVm.getOutlookViewItem(OutlookObject.DESC4)).setText(descArray[3]);
    
    ((TextView)wVm.getOutlookViewItem(OutlookObject.LABEL)).setText("Weather Outlook for Panama City");
  }
}
