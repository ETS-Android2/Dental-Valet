package com.dentalvalet.dentalvaletApp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.dentalvalet.dentalvaletApp.Adaptors.TestimonialViewAdaptop;
import com.dentalvalet.dentalvaletApp.Adaptors.TreatmentPlanViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Model.TreatmentPlanModel;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class Testimonial extends FragmentActivity  implements TestimonialViewAdaptop.clickListener{

    static final int REQUEST_VIDEO_CAPTURE = 1;
    Button backBtn;
     private RecyclerView recyclerView;
    private TestimonialViewAdaptop adaptor;
    VideoView mVideoView;
    Button testimonial_record_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testimonial);


        testimonial_record_btn = (Button)findViewById(R.id.testimonial_record_btn);
        recyclerView = (RecyclerView) findViewById(R.id.testimonial_list);
        adaptor = new TestimonialViewAdaptop(Testimonial.this);
        adaptor.setTestionialInfo(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistTestimonial());
        adaptor.setClickListener(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(Testimonial.this));


        testimonial_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });

        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_testominoal")) {
                    finish();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_testominoal"));

        backBtn = (Button)findViewById(R.id.dentist_refer_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Testimonial.this, ReferDentist.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void itemCicked(View v, int position) {

    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();

            Log.v("MOULIA", videoUri.getPath());
            //Toast.makeText(Testimonial.this, videoUri.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
