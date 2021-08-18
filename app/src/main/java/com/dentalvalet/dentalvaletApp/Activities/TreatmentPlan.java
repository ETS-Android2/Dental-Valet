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
import com.dentalvalet.dentalvaletApp.Adaptors.TreatmentPlanViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Model.TreatmentPlanModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class TreatmentPlan extends FragmentActivity  implements TreatmentPlanViewAdaptor.clickListener{

    TextView name;
    TextView phone;
    TextView city;
    TextView email;
    ImageView dentistImage;
    TextView treatmentPlanDescription;
    DentistInfo patientsDentist;
    private ImageLoader imageLoader;
    Button backBtn;
    ArrayList<TreatmentPlanModel> patientTreatmentPlan;

    private RecyclerView recyclerView;
    private TreatmentPlanViewAdaptor adaptor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (MyApplication.getsInstance().getCurrentUser().getPatientTreatmentPlan() == null) {
            setContentView(R.layout.empty_wishlist);
        } else {
            if (MyApplication.getsInstance().getCurrentUser().getPatientTreatmentPlan().isEmpty()) {
                setContentView(R.layout.empty_wishlist);
            } else {
                setContentView(R.layout.treatment_plan);

                recyclerView = (RecyclerView) findViewById(R.id.treatment_plan_list);
                adaptor = new TreatmentPlanViewAdaptor(TreatmentPlan.this);
                adaptor.setTreatmentPlanInfo(MyApplication.getsInstance().getCurrentUser().getPatientTreatmentPlan());
                adaptor.setClickListener(this);
                recyclerView.setAdapter(adaptor);
                recyclerView.setLayoutManager(new LinearLayoutManager(TreatmentPlan.this));

                name = (TextView)findViewById(R.id.dentist_name);
                phone = (TextView)findViewById(R.id.dentist_phone);
                email = (TextView)findViewById(R.id.dentist_email);
                dentistImage = (ImageView)findViewById(R.id.dentist_image);
                treatmentPlanDescription =  (TextView)findViewById(R.id.treatment_plan_description);

                patientTreatmentPlan = MyApplication.getsInstance().getCurrentUser().getPatientTreatmentPlan();
                patientsDentist = MyApplication.getsInstance().getCurrentUser().getSelectedDentist();
                name.setText(patientsDentist.getName());
                phone.setText(patientsDentist.getMobile());
                email.setText(patientsDentist.getEmail());

                if(patientTreatmentPlan.size()>0)
                {
                    treatmentPlanDescription.setText(patientTreatmentPlan.get(0).getDescription());
                }


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
                                Bitmap bm  = response.getBitmap();//MyApplication.getsInstance().getCircleBitmap(response.getBitmap());
                                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.dashboard_picture_bg, null);
                                int h = d.getIntrinsicHeight();
                                int w = d.getIntrinsicWidth();
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

                backBtn = (Button)findViewById(R.id.dentist_refer_btn);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TreatmentPlan.this, ReferDentist.class);
                        startActivity(intent);
                    }
                });


            }
        }






        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_TreatmentPlane")) {
                    finish();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_TreatmentPlane"));

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
