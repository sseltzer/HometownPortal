package com.dylf.hometown.moduleitems.weather;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageRetriever extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public ImageRetriever(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... url) {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(url[0]).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
