package com.dentalvalet.dentalvaletApp.Activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 04-Dec-15.
 */
public class PracticeInformation extends FragmentActivity {

    TextView name;
    TextView phone;
    TextView address;
    TextView email;
    TextView practiceInfo;
    TextView uniqueInfo;
    TextView insuranceInfo;
    DentistInfo patientsDentist;
    ImageView dentistImage;
    private ImageLoader imageLoader;
    ImageView backBtn;
    Button dashboard_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_information);
        name = (TextView)findViewById(R.id.dentist_name);
        phone = (TextView)findViewById(R.id.dentist_phone);
        address = (TextView)findViewById(R.id.dentist_address);
        email = (TextView)findViewById(R.id.dentist_email);
        dentistImage= (ImageView)findViewById(R.id.dentist_image);
        practiceInfo= (TextView)findViewById(R.id.practice_info_description);
        uniqueInfo= (TextView)findViewById(R.id.unique_info);
        insuranceInfo= (TextView)findViewById(R.id.practice_info_insurance);
        dashboard_btn= (Button)findViewById(R.id.dashboard_btn);


        patientsDentist = MyApplication.getsInstance().getCurrentUser().getSelectedDentist();
        name.setText(patientsDentist.getName());
        phone.setText(patientsDentist.getMobile());
        address.setText(patientsDentist.getOfficeAddress());
        email.setText(patientsDentist.getEmail());
        practiceInfo.setText(patientsDentist.getPractiseInfo());
        uniqueInfo.setText(patientsDentist.getUniqueInfo());
        insuranceInfo.setText(patientsDentist.getInsuranceAccepted());


        dashboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

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




    }
    @Override
    public void onBackPressed() {
        finish();
        return;
    }
}

