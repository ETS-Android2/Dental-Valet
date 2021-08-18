package com.dentalvalet.dentalvaletApp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dentalvalet.dentalvaletApp.Adaptors.AppointmentViewAdaptor;
import com.dentalvalet.dentalvaletApp.Adaptors.ServicesViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class Services extends FragmentActivity implements ServicesViewAdaptor.clickListener {
    private RecyclerView recyclerView;
    private ServicesViewAdaptor adaptor;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(MyApplication.getsInstance().getAllServices()==null)
        {
            setContentView(R.layout.empty_services);
        }
        else {
            if (MyApplication.getsInstance().getAllServices().isEmpty()) {
                setContentView(R.layout.empty_services);
            } else {

                setContentView(R.layout.services);
                recyclerView = (RecyclerView) findViewById(R.id.services_list);
                adaptor = new ServicesViewAdaptor(Services.this);
                adaptor.setServicesInfo(MyApplication.getsInstance().getAllServices());
                adaptor.setClickListener(this);
                recyclerView.setAdapter(adaptor);
                recyclerView.setLayoutManager(new LinearLayoutManager(Services.this));


                BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

                    @Override
                    public void onReceive(Context arg0, Intent intent) {
                        String action = intent.getAction();
                        if (action.equals("finish_services")) {
                            finish();
                            // DO WHATEVER YOU WANT.
                        }
                    }
                };
                registerReceiver(broadcast_reciever, new IntentFilter("finish_services"));

                backBtn = (Button)findViewById(R.id.dentist_refer_btn);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Services.this, ReferDentist.class);
                        startActivity(intent);
                    }
                });
            }
        }




    }

    @Override
    public void itemCicked(View v, int position) {

    }

    @Override
    public void onBackPressed() {
       finish();
        return;
    }
}
