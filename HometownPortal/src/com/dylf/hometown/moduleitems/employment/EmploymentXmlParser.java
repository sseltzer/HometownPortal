/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

// This resource was taken from the android developer website

package com.dylf.hometown.moduleitems.employment;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses XML feeds from indeed.com.
 * Given an InputStream representation of a feed, it returns a List of reults,
 * where each list element represents a single result (post) in the XML feed.
 */
public class EmploymentXmlParser {
    private static final String ns = null;

    // We don't use namespaces

    public List<Result> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readResponse(parser);
        } finally {
            in.close();
        }
    }

    private List<Result> readResponse(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Result> results = new ArrayList<Result>();

        parser.require(XmlPullParser.START_TAG, ns, "response");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("results")) {
            	name = parser.getName();
            } else if (name.equals("result")) {
                results.add(readResult(parser));
            } else {
                skip(parser);
            }
        }
        return results;
    }

    // This class represents a single result (post) in the XML response.
    // It includes the data members "jobtitle," "company," "formattedLocation," "date,"
    // "snippet," and "url."
    public static class Result {
        public final String jobtitle;
        public final String company;
        public final String formattedLocation;
        public final String date;
        public final String snippet;
        public final String url;

        private Result(String jobtitle, String company, String formattedLocation, String date, String snippet, String url) {
            this.jobtitle = jobtitle;
            this.company = company;
            this.formattedLocation = formattedLocation;
            this.date = date;
            this.snippet = snippet;
            this.url = url;
        }
    }

    // Parses the contents of a result. If it encounters a jobtitle, company, formattedLocation, 
    // date, snippet or url tag, hands them off to their respective methods for processing.
    //  Otherwise, skips the tag.
    private Result readResult(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "result");
        String jobtitle = null;
        String company = null;
        String formattedLocation = null;
        String date = null;
        String snippet = null;
        String url = null;
        
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("jobtitle")) {
                jobtitle = readJobtitle(parser);
            } else if (name.equals("company")) {
                company = readCompany(parser);
            } else if (name.equals("formattedLocation")) {
                formattedLocation = readFormattedLocation(parser);
            } else if (name.equals("date")) {
            	date = readDate(parser);
            } else if (name.equals("snippet")) {
            	snippet = readSnippet(parser);
            } else if (name.equals("url")) {
            	url = readUrl(parser);
            } else {
                skip(parser);
            }
        }
        return new Result(jobtitle, company, formattedLocation, date, snippet, url);
    }

    // Processes jobtitle tags in the response.
    private String readJobtitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "jobtitle");
        String jobtitle = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "jobtitle");
        return jobtitle;
    }

    // Processes company tags in the response.
    private String readCompany(XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, "company");
        String company = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "company");
        return company;
    }

    // Processes formattedLocation tags in the response.
    private String readFormattedLocation(XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, "formattedLocation");
        String formattedLocation = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "formattedLocation");
        return formattedLocation;
    }
    
    // Processes company tags in the response.
    private String readDate(XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, "date");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "date");
        return date;
    }
    
    // Processes snippet tags in the response.
    private String readSnippet(XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, "snippet");
        String snippet = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "snippet");
        return snippet;
    }
    
    // Processes url tags in the response.
    private String readUrl(XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, "url");
        String url = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "url");
        return url;
    }
    
    // For the tags extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
