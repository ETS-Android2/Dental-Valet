package com.dentalvalet.dentalvaletApp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 20-Nov-15.
 */
public class SplashScreen extends FragmentActivity {

    private static int SPLASH_TIME_OUT = 1000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        MyApplication.getsInstance().hashKeyForFB();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash_bg screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, OpeningScreen1.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }



}
