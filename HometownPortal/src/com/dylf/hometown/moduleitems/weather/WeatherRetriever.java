package com.dylf.hometown.moduleitems.weather;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.dylf.hometown.R;

public class WeatherRetriever extends AsyncTask<String, Void, JSONObject> {
    JSONObject data;
    
    protected JSONObject doInBackground(String... url) {
      JSONParser parse = new JSONParser();
      data = parse.getJSONFromUrl(url[0]);
      return data;
    }
    
    protected void onPostExecute(JSONObject data) {
      /*
      TextView location = (TextView) findViewById(R.id.outlookLabel);
      TextView currTemp = (TextView) findViewById(R.id.Temp);
      TextView currDesc = (TextView) findViewById(R.id.currdescription);
      TextView feelLike = (TextView) findViewById(R.id.feels);
      TextView windSpd  = (TextView) findViewById(R.id.windspd);
      TextView windDir  = (TextView) findViewById(R.id.winddir);
      TextView humidity = (TextView) findViewById(R.id.humid);
      double cTemp = 0;
      String desc = "";
      double cFeel = 0;
      double cWindSp = 0;
      String cWindDir = "";
      String cHumid = "";
      String iconUrl = "";
      
      try{
        JSONObject currentCond = data.getJSONObject("current_observation");
        cTemp = currentCond.getDouble("temp_f");
        desc = currentCond.getString("weather");
        cFeel = currentCond.getDouble("feelslike_f");
        cWindSp = currentCond.getDouble("wind_mph");
        cWindDir = currentCond.getString("wind_dir");
        cHumid = currentCond.getString("relative_humidity");
        iconUrl = currentCond.getString("icon_url");
      }catch(Throwable e) {e.printStackTrace();}
      
      try{
        JSONObject locData1 = data.getJSONObject("current_observation");
        JSONObject locData2 = locData1.getJSONObject("display_location");
        cityState = locData2.getString("full");
        
      }catch(Throwable e) {e.printStackTrace();}
      
      String tempTemp = new String(Double.toString(cTemp) + (char) 0x00B0);
      String tempFeel = new String("Feels like: " + Double.toString(cFeel) + (char) 0x00B0);
      String tempWindSp = new String("Wind: " + Double.toString(cWindSp) + "MPH");
      String tempWindD = new String("Dir: " + cWindDir);
      String tempHumid = new String("Humidity: " + cHumid);
      String tempLoc = new String("Current Conditions in " + cityState);
      
      location.setText(tempLoc);
      currTemp.setText(tempTemp);
      currDesc.setText(desc);
      feelLike.setText(tempFeel);
      windSpd.setText(tempWindSp);
      windDir.setText(tempWindD);
      humidity.setText(tempHumid);
      new DownloadImageTask((ImageView) findViewById(R.id.weathericon))
          .execute(iconUrl);
          
    }
    */
  }
}
