// Most of this code came from the android developer website

package com.dylf.hometown.moduleitems.employment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.dylf.hometown.R;
import com.dylf.hometown.moduleitems.employment.EmploymentXmlParser.Result;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class EmploymentDisplayManager {
	private final String URL = "http://api.indeed.com/ads/apisearch?publisher=7386450885104269&q=&l=panama+city%2C+fl&sort=date&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
	private WebView myWebView;
	private Context context;
	private LinearLayout currentAnchor;
	
	
	public EmploymentDisplayManager(Context ct, Bundle savedInstanceState) {
		context = ct;
		loadPage();
	}
	
	private void loadPage() {
		new DownloadXmlTask().execute(URL);
	}


	// Implementation of AsyncTask used to download XML feed from stackoverflow.com.
	private class DownloadXmlTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			try {
				return loadXmlFromNetwork(urls[0]);
			} catch (IOException e) {
				return context.getResources().getString(R.string.connection_error);
			} catch (XmlPullParserException e) {
				return context.getResources().getString(R.string.xml_error);
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// Displays the HTML string in the UI via a WebView
			myWebView = new WebView(context);
			myWebView.loadData(result, "text/html", null);
		}
	}

	public void requestAttach(LinearLayout anchor) {
	    if (currentAnchor != null || anchor == null) return;
	    currentAnchor = anchor;
	    anchor.addView(myWebView);
	  }
	
	public void requestDetach(LinearLayout anchor) {
	    if (currentAnchor == null || !currentAnchor.equals(anchor)) return;
	    currentAnchor.removeView(myWebView);
	    currentAnchor = null;
	}
	
	// Uploads XML from stackoverflow.com, parses it, and combines it with
	// HTML markup. Returns HTML string.
	private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
		InputStream stream = null;
		EmploymentXmlParser employmentXmlParser = new EmploymentXmlParser();
		List<Result> results = null;
		
		String jobtitle = context.getResources().getString(R.string.jobtitle);
		String company = context.getResources().getString(R.string.company);
		String location = context.getResources().getString(R.string.location);
		String dateposted = context.getResources().getString(R.string.dateposted);
		String description = context.getResources().getString(R.string.description);
		String link = context.getResources().getString(R.string.link);
				
		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<span id=indeed_at><a href=\"http://www.indeed.com/\">jobs</a> by ");
		htmlString.append("<a href=\"http://www.indeed.com/\" title=\"Job Search\">");
		htmlString.append("<img src=\"http://www.indeed.com/p/jobsearch.gif\" style=\"border: 0;");
		htmlString.append("vertical-align: middle;\" alt=\"Indeed job search\"></a></span>");
		try {
            stream = downloadUrl(urlString);
            results = employmentXmlParser.parse(stream);
        // Makes sure that the InputStream is closed after the app is
        // finished using it.
		} finally {
            if (stream != null) {
                stream.close();
            }
        }
				
		// EmploymentXmlParser returns a List (called "results") of Result objects.
		// Each Result object represents a single post in the XML feed.
		// This section processes the results list to combine each entry with HTML markup.
		// Each entry is displayed in the UI 
		for (Result result : results) {
			htmlString.append("<hr>");
			htmlString.append("<p><b>" + jobtitle + "</b> " + result.jobtitle + "</p>");
			htmlString.append("<p><b>" + company + "</b> " + result.company + "</p>");
			htmlString.append("<p><b>" + location + "</b> " + result.formattedLocation + "</p>");
			htmlString.append("<p><b>" + dateposted + "</b> " + result.date + "</p>");
			htmlString.append("<p><b>" + description + "</b> " + result.snippet + "</p>");
			htmlString.append("<p><a href='");
			htmlString.append(result.url);
			htmlString.append("'><b>" + link + "</b></a></p>");
		} 
		
		return htmlString.toString();
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();
		InputStream stream = conn.getInputStream();
		return stream;
	}

}
