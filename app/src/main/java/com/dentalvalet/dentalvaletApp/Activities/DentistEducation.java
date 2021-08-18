package com.dentalvalet.dentalvaletApp.Activities;

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
import com.dentalvalet.dentalvaletApp.Adaptors.EducationViewAdaptor;
import com.dentalvalet.dentalvaletApp.Adaptors.ExperienceViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 04-Dec-15.
 */
public class DentistEducation extends FragmentActivity implements EducationViewAdaptor.clickListener,ExperienceViewAdaptor.clickListener  {

    private RecyclerView recyclerView;
    private EducationViewAdaptor adaptor;


    private RecyclerView recyclerView1;
    private ExperienceViewAdaptor adaptor1;
    ImageView backBtn;

    TextView name;
    TextView phone;
    TextView address;
    DentistInfo patientsDentist;
    TextView email;
    ImageView dentistImage;
    private ImageLoader imageLoader;
    Button dashboard_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dentist_education);

        recyclerView = (RecyclerView) findViewById(R.id.education_list);
        adaptor = new EducationViewAdaptor(DentistEducation.this);
        adaptor.setEducationInfos(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistEducation());
        adaptor.setClickListener(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(DentistEducation.this));


        recyclerView1 = (RecyclerView) findViewById(R.id.experience_list);
        adaptor1 = new ExperienceViewAdaptor(DentistEducation.this);
        adaptor1.setExperienceInfos(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistExperience());
        adaptor1.setClickListener(this);
        recyclerView1.setAdapter(adaptor1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(DentistEducation.this));




        name = (TextView)findViewById(R.id.dentist_name);
        phone = (TextView)findViewById(R.id.dentist_phone);
        address = (TextView)findViewById(R.id.dentist_address);
        email = (TextView)findViewById(R.id.dentist_email);
        dentistImage= (ImageView)findViewById(R.id.dentist_image);
        dashboard_btn= (Button)findViewById(R.id.dashboard_btn);



        patientsDentist = MyApplication.getsInstance().getCurrentUser().getSelectedDentist();
        name.setText(patientsDentist.getName());
        phone.setText(patientsDentist.getMobile());
        address.setText(patientsDentist.getOfficeAddress());
        email.setText(patientsDentist.getEmail());





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
        finish();
        return;
    }
}
