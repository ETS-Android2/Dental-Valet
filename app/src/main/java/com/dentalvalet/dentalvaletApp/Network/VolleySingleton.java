package com.dentalvalet.dentalvaletApp.Network;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Awais Mahmood on 21-Nov-15.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue nRequestQueue;
    private ImageLoader imageLoader;


    private VolleySingleton() {
        nRequestQueue = new Volley().newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(nRequestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);


            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getsInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;

    }

    public RequestQueue getRequestQueue() {
        return nRequestQueue;
    }


    public ImageLoader getImageLoader() {
        return imageLoader;
    }


    public static Bitmap loadImageFromUrl(String url) {

        Bitmap bm;
        try {

            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();

            conn.connect();
            InputStream is = null;
            try
            {
                is= conn.getInputStream();
            }catch(IOException e)
            {
                return null;
            }

            BufferedInputStream bis = new BufferedInputStream(is);

            bm = BitmapFactory.decodeStream(bis);

            bis.close();
            is.close();

        } catch (IOException e) {
            return null;
        }

        return  Bitmap.createScaledBitmap(bm,100,100,true);


    }


}

