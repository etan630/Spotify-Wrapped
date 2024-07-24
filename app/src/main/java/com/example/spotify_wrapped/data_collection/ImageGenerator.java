package com.example.spotify_wrapped.data_collection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.URL;

/**
 * ImageGenerator class which will generate an image on a view based on a link
 */
public class ImageGenerator{
    /**
     * generator to put image from web on view
     * @param link link of the image
     * @param view view to put the image on
     * @param xDimension x dimension of the size of image
     * @param yDimension y dimension of the size of image
     */
    public static void generate(String link, ImageView view, int xDimension, int yDimension){
        try {
            URL url = new URL(link);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            view.setImageBitmap(Bitmap.createScaledBitmap(bmp, xDimension, yDimension, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}