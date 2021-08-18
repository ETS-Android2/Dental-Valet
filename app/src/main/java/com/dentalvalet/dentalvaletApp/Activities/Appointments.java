package com.dentalvalet.dentalvaletApp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dentalvalet.dentalvaletApp.Adaptors.AppointmentViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class Appointments extends FragmentActivity implements AppointmentViewAdaptor.clickListener{

    private RecyclerView recyclerView;
    private AppointmentViewAdaptor adaptor;
    TextView name;
    TextView phone;
    TextView address;
    TextView email;
    ImageView dentistImage;
    DentistInfo patientsDentist;
    private ImageLoader imageLoader;
    ImageView backBtn;
    Button dashboard_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appintments);

        recyclerView = (RecyclerView) findViewById(R.id.appointment_list);
        adaptor = new AppointmentViewAdaptor(Appointments.this);
        adaptor.setAppointmentInfos(MyApplication.getsInstance().getAllAppointments());
        adaptor.setClickListener(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(Appointments.this));




        name = (TextView)findViewById(R.id.dentist_name);
        phone = (TextView)findViewById(R.id.dentist_phone);
        address = (TextView)findViewById(R.id.dentist_address);
        email = (TextView)findViewById(R.id.dentist_email);
        dentistImage = (ImageView)findViewById(R.id.dentist_image);
        dashboard_btn = (Button)findViewById(R.id.dashboard_btn);

        patientsDentist = MyApplication.getsInstance().getCurrentUser().getSelectedDentist();
        name.setText(patientsDentist.getName());
        phone.setText(patientsDentist.getMobile());
        address.setText(patientsDentist.getOfficeAddress());
        email.setText(patientsDentist.getEmail());

        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_appointment")) {
                    finish();
                    return;
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_appointment"));



        imageLoader = VolleySingleton.getsInstance().getImageLoader();

        String imageUrl = Keys.URL_DENTIST_PROFILE +patientsDentist.getImage();
        if(imageUrl!=null)
        {
            Log.v("MOULIA", imageUrl);
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if(response.getBitmap() !=null)
                    {
                        Bitmap bm  = MyApplication.getsInstance().getCircleBitmap(response.getBitmap());
                        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.circle_for_pic, null);
                        int h = d.getIntrinsicHeight()+20;
                        int w = d.getIntrinsicWidth()+20;
                        bm =  Bitmap.createScaledBitmap(bm, h, w, true);
                        dentistImage.setImageBitmap(bm);
                    }
                    else
                    {
                        //set Some loader image here
                    }

                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

        dashboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent("finish_promotion"); //for finish of patient_profile activity
                sendBroadcast(intent1);
                Intent intent2 = new Intent("finish_services"); //for finish of patient_profile activity
                sendBroadcast(intent2);
                finish();
                return;
            }
        });



    }

    @Override
    public void itemCicked(View v, int position) {

    }
    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
        finish();
        return;

    }

}
