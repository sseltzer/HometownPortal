package com.dylf.hometown.moduleitems.mapmanager;

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
import android.util.Log;

//http://mobile.tutsplus.com/tutorials/android/android-sdk-working-with-google-maps-google-places-integration/
public class PlacesRetriever extends AsyncTask<String, Void, String> {
  StringBuilder responseBuilder = new StringBuilder();

  @Override
  protected String doInBackground(String... urls) {
    for (String url : urls) {
      Log.d("debug", "Fetching: " + url);
      HttpClient placesClient = new DefaultHttpClient();
      try {
        HttpGet placesGet = new HttpGet(url);
        HttpResponse placesResponse = placesClient.execute(placesGet);
        StatusLine placeSearchStatus = placesResponse.getStatusLine();
        if (placeSearchStatus.getStatusCode() == 200) {
          HttpEntity placesEntity = placesResponse.getEntity();
          InputStream placesContent = placesEntity.getContent();
          InputStreamReader placesInput = new InputStreamReader(placesContent);
          BufferedReader placesReader = new BufferedReader(placesInput);
          String lineIn;
          while ((lineIn = placesReader.readLine()) != null) responseBuilder.append(lineIn);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return responseBuilder.toString();
  }

}
