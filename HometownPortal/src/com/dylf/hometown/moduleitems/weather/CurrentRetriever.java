package com.dylf.hometown.moduleitems.weather;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.dylf.hometown.moduleitems.weather.WeatherViewManager.CurrentObject;

public class CurrentRetriever extends AsyncTask<String, Void, JSONObject> {
  JSONObject data;

  private WeatherViewManager wVm;

  public CurrentRetriever(WeatherViewManager wVm) {
    this.wVm = wVm;
  }

  protected JSONObject doInBackground(String... url) {
    JSONParser parse = new JSONParser();
    data = parse.getJSONFromUrl(url[0]);
    return data;
  }

  protected void onPostExecute(JSONObject data) {
    double cTemp = 0;
    String desc = "";
    double cFeel = 0;
    double cWindSp = 0;
    String cWindDir = "";
    String cHumid = "";
    String iconUrl = "";
    String cityState = "";

    try {
      JSONObject currentCond = data.getJSONObject("current_observation");
      cTemp = currentCond.getDouble("temp_f");
      desc = currentCond.getString("weather");
      cFeel = currentCond.getDouble("feelslike_f");
      cWindSp = currentCond.getDouble("wind_mph");
      cWindDir = currentCond.getString("wind_dir");
      cHumid = currentCond.getString("relative_humidity");
      iconUrl = currentCond.getString("icon_url");
    } catch (Throwable e) {
      e.printStackTrace();
    }

    try {
      JSONObject locData1 = data.getJSONObject("current_observation");
      JSONObject locData2 = locData1.getJSONObject("display_location");
      cityState = locData2.getString("full");

    } catch (Throwable e) {
      e.printStackTrace();
    }

    String tempTemp = new String(Double.toString(cTemp) + (char) 0x00B0);
    String tempFeel = new String("Feels like: " + Double.toString(cFeel) + (char) 0x00B0);
    String tempWindSp = new String("Wind: " + Double.toString(cWindSp) + "MPH");
    String tempWindD = new String("Dir: " + cWindDir);
    String tempHumid = new String("Humidity: " + cHumid);
    String tempLoc = new String("Current Conditions in " + cityState);

    ((TextView) wVm.getCurrentViewItem(CurrentObject.CURRENT)).setText(tempLoc);
    ((TextView) wVm.getCurrentViewItem(CurrentObject.TEMP)).setText(tempTemp);
    ((TextView) wVm.getCurrentViewItem(CurrentObject.CURDESC)).setText(desc);
    ((TextView) wVm.getCurrentViewItem(CurrentObject.FEELS)).setText(tempFeel);
    ((TextView) wVm.getCurrentViewItem(CurrentObject.WINDSPD)).setText(tempWindSp);
    ((TextView) wVm.getCurrentViewItem(CurrentObject.WINDDIR)).setText(tempWindD);
    ((TextView) wVm.getCurrentViewItem(CurrentObject.HUMID)).setText(tempHumid);

    new ImageRetriever((ImageView) wVm.getCurrentViewItem(CurrentObject.WICON))
        .execute(iconUrl);
  }
}
