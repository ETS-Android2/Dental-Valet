package com.dentalvalet.dentalvaletApp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;
import com.facebook.FacebookSdk;

/**
 * Created by Awais Mahmood on 24-Nov-15.
 */
public class OpeningScreen3  extends FragmentActivity{
View head;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.opening_screen_3);


        head= (View)findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getAppContext(), OpeningScreen4.class);
                startActivity(intent);
                finish();
            }
        });
        // Other app specific specialization


    }
}
