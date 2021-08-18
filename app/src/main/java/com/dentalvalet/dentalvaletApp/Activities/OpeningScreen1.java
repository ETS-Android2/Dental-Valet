package com.dentalvalet.dentalvaletApp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;
import com.facebook.CallbackManager;

import org.json.JSONObject;

/**
 * Created by Awais Mahmood on 23-Nov-15.
 */
public class OpeningScreen1 extends FragmentActivity {
    RequestQueue queue;
    JSONObject jsonObject;
    JSONObject faceBookResponse;
    ImageView fbBtn;
    ImageView cntnue;
    View head;
    private TextView textView;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_screen_1);
       boolean hasLoggedIn =  MyApplication.getsInstance().getSharedPrefs();
        if(hasLoggedIn)
        {
            //Go directly to main activity.

            Intent intent = new Intent(MyApplication.getAppContext(), SplashLogin.class);
            startActivity(intent);
            finish();
        }


        head = (View)findViewById(R.id.head);

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getAppContext(), OpeningScreen2.class);
                startActivity(intent);
                finish();
            }
        });


        // Other app specific specialization


    }


}
