package com.dentalvalet.dentalvaletApp.Activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.android.volley.RequestQueue;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Fragments.FindDentistFragment;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by Awais Mahmood on 19-Nov-15.
 */
public class ListDentist extends AppCompatActivity {


    RequestQueue queue;
    Button searchFilter;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ImageView logout_btn;
    private Toolbar toolbar;
    private FindDentistFragment dentistFragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_dentist);
        logout_btn = (ImageView)findViewById(R.id.logout_btn);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new FindDentistFragment(),"DENTIST_LIST").commit();

        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_dentist_list")) {
                    finish();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_dentist_list"));



//        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
        searchFilter = (Button) findViewById(R.id.search_filter);
        searchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Dialog mDialog = new Dialog(ListDentist.this, android.R.style.Theme_Translucent_NoTitleBar);
//
//                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//                mDialog.setContentView(R.layout.search_dentist);
//                mDialog.show();
                Intent intent = new Intent(ListDentist.this, SearchDentist.class);
                startActivity(intent);

            }
        });

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(MyApplication.getsInstance().isSearchFilter())
        {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag("DENTIST_LIST");
            if(fragment != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, new FindDentistFragment(),"DENTIST_LIST").commit();
            }

        }
       // Toast.makeText(ListDentist.this, "OnResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.getsInstance().setSearchFilter(false);
       // Toast.makeText(ListDentist.this, "OnPause", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onBackPressed() {
        //  moveTaskToBack(true);
    }



}
