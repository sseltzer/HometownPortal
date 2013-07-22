package com.dylf.hometown.moduleitems.MapManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

//http://mobile.tutsplus.com/tutorials/android/android-sdk-working-with-google-maps-google-places-integration/
public class GetPlace extends AsyncTask<String, Void, String> {
  StringBuilder placesBuilder = new StringBuilder();

  @Override
  protected String doInBackground(String... placesURL) {
    for (String placeSearchURL : placesURL) {
      HttpClient placesClient = new DefaultHttpClient();
      try {
        HttpGet placesGet = new HttpGet(placeSearchURL);
        HttpResponse placesResponse = placesClient.execute(placesGet);
        StatusLine placeSearchStatus = placesResponse.getStatusLine();
        if (placeSearchStatus.getStatusCode() == 200) {
          HttpEntity placesEntity = placesResponse.getEntity();
          InputStream placesContent = placesEntity.getContent();
          InputStreamReader placesInput = new InputStreamReader(placesContent);
          BufferedReader placesReader = new BufferedReader(placesInput);
          String lineIn;
          while ((lineIn = placesReader.readLine()) != null) placesBuilder.append(lineIn);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return placesBuilder.toString();
  }

}
