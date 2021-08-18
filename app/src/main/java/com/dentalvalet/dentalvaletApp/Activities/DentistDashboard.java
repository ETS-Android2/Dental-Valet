package com.dentalvalet.dentalvaletApp.Activities;

import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Model.PromotionModel;
import com.dentalvalet.dentalvaletApp.Model.TestimonialModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class DentistDashboard extends FragmentActivity {


    TextView name;
    TextView phone;
    TextView address;
    TextView email;
    DentistInfo patientsDentist;
    ImageView dentistImage;
    private ImageLoader imageLoader;
    ImageView backBtn;
    ImageView services;

    ImageView practiceInfo;
    ImageView education;
    RequestQueue queue;
    ImageView promotions;
    ImageView testimonial;
    ImageView staff;
    Button dashboard_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dentist_dashboard);
        name = (TextView)findViewById(R.id.dentist_name);
        phone = (TextView)findViewById(R.id.dentist_phone);
        address = (TextView)findViewById(R.id.dentist_address);
        email = (TextView)findViewById(R.id.dentist_email);
       dentistImage= (ImageView)findViewById(R.id.dentist_image);
        practiceInfo= (ImageView)findViewById(R.id.dashboard_practice_info);
        education= (ImageView)findViewById(R.id.dashboard_education);
        services= (ImageView)findViewById(R.id.dashboard_services);

        promotions = (ImageView)findViewById(R.id.dashboard_promotion);

        testimonial = (ImageView)findViewById(R.id.dashboard_testimonial);
        staff = (ImageView)findViewById(R.id.dashboard_staff);
        dashboard_btn = (Button)findViewById(R.id.dashboard_btn);



        patientsDentist = MyApplication.getsInstance().getCurrentUser().getSelectedDentist();
        name.setText(patientsDentist.getName());
        phone.setText(patientsDentist.getMobile());
        address.setText(patientsDentist.getOfficeAddress());
        email.setText(patientsDentist.getEmail());



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


        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DentistDashboard.this, Services.class);
                startActivity(intent);
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DentistDashboard.this, DentistStaff.class);
                startActivity(intent);
            }
        });


        practiceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DentistDashboard.this, PracticeInformation.class);
                startActivity(intent);
            }
        });



        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DentistDashboard.this, DentistEducation.class);
                startActivity(intent);

            }
        });

        promotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistPromotions() == null) {
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendPromotionJsonRequest(String.valueOf(patientsDentist.getId()));

                }
                else
                {
                    Intent intent = new Intent(DentistDashboard.this, Promotions.class);
                    startActivity(intent);

                }



            }
        });

        testimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistTestimonial() == null) {

                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendTestimonialJsonRequest(String.valueOf(patientsDentist.getId()));

                }
                else
                {
                    Intent intent = new Intent(DentistDashboard.this, Testimonial.class);
                    startActivity(intent);
                }





            }
        });


    }


    public void sendPromotionJsonRequest(String dentistId) {
        String url = getPromotionRequestUrl(dentistId, Keys.Status.PROMOTION_DENTIST_BY_PATIENT_ID);
        Log.v("MOULIA", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null || response.length() == 0) {
                            Toast.makeText(MyApplication.getAppContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        response = response.replace("\\", "");
                        response = response.replace("/", "");
                        response = response.substring(1, response.length() - 1);
                        Log.v("MOULIA", response);

                        if (response.equals("0")) {
                            Log.v("MOULIA", "No Promotions");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<PromotionModel> promotionInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentPromotion = jsonArray.getJSONObject(i);

                                    PromotionModel info = new PromotionModel(
                                            currentPromotion.getString("Id"),
                                            currentPromotion.getString("dentistId"),
                                            currentPromotion.getString("title"),
                                            currentPromotion.getString("description"),
                                            currentPromotion.getString("cost"),
                                            currentPromotion.getString("validityDate"),
                                            currentPromotion.getString("image"),
                                            currentPromotion.getString("rating")
                                    );

                                    promotionInfos.add(info);


                                }

                                if (promotionInfos != null) {
                                    MyApplication.getsInstance().getCurrentUser().getSelectedDentist().setDentistPromotions(promotionInfos);
                                }

                                Intent intent = new Intent(DentistDashboard.this, Promotions.class);
                                startActivity(intent);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("MOULIA", error.getMessage());
                Toast.makeText(MyApplication.getAppContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getPromotionRequestUrl(String dentistId,int status) {
        // http://dentalvalet.com/api/app?dentistId=3&status=9  promotions
        return Keys.URL_DENTAL_VALET_APP+
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_DENTIST_ID+
                dentistId+
                Keys.URL_CHAR_AMPERSAND+
                Keys.URL_PARAM_STATUS +
                status;

    }



    public void sendTestimonialJsonRequest(String dentistId) {
        String url = getTestimonialRequestUrl(dentistId, Keys.Status.TESTIMONIAL);
        Log.v("MOULIA", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null || response.length() == 0) {
                            Toast.makeText(MyApplication.getAppContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        response = response.replace("\\", "");
                        response = response.replace("/", "");
                        response = response.substring(1, response.length() - 1);
                        Log.v("MOULIA", response);

                        if (response.equals("0")) {
                            Log.v("MOULIA", "No Promotions");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<TestimonialModel> testimonialInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentTestimonial = jsonArray.getJSONObject(i);

                                    TestimonialModel info = new TestimonialModel(
                                            currentTestimonial.getString("Id"),
                                            currentTestimonial.getString("patientId"),
                                            currentTestimonial.getString("dentistId"),
                                            currentTestimonial.getString("title"),
                                            currentTestimonial.getString("description"),
                                            currentTestimonial.getString("createdAt"),
                                            currentTestimonial.getString("status"),
                                            currentTestimonial.getString("approved"),
                                            currentTestimonial.getString("videoFile")
                                    );

                                    testimonialInfos.add(info);


                                }

                                if (testimonialInfos!= null) {
                                    MyApplication.getsInstance().getCurrentUser().getSelectedDentist().setDentistTestimonial(testimonialInfos);
                                }

                                Intent intent = new Intent(DentistDashboard.this, Testimonial.class);
                                startActivity(intent);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("MOULIA", error.getMessage());
                Toast.makeText(MyApplication.getAppContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getTestimonialRequestUrl(String dentistId,int status) {
        // http://dentalvalet.com/api/App?status=31&dentistId=3    Testimonial
        return "http://dentalvalet.com/api/App"+
                Keys.URL_CHAR_QUESTION +
                Keys.URL_PARAM_STATUS +
                status+
                Keys.URL_CHAR_AMPERSAND+
                Keys.Attributes.KEY_DENTIST_ID+
                dentistId;

    }





    @Override
    public void onBackPressed() {
      finish();
        return;
    }
}
