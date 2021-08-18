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
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.PromotionModel;
import com.dentalvalet.dentalvaletApp.Model.RewardsModel;
import com.dentalvalet.dentalvaletApp.Model.TestimonialModel;
import com.dentalvalet.dentalvaletApp.Model.TreatmentPlanModel;
import com.dentalvalet.dentalvaletApp.Model.WishlistModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 27-Nov-15.
 */
public class PatientDashboard extends FragmentActivity{

    TextView name;
    TextView phone;
    TextView address;
    TextView email;
    DentistInfo patientsDentist;
    ImageView patientImage;
    ImageView appointment;
    ImageView services;
    ImageView treatmentPlan;
    ImageView wishlist;
    ImageView promotions;
    ImageView testimonial;
    ImageView rewards;
    ImageView myDentist;
    ImageView myProfile;
    ImageView logout_btn;
    Button testProfile;
    private ImageLoader imageLoader;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_dashboard);
        MyApplication.getsInstance().storeSharedPrefs();//just to save that this user has logged IN and next time start screens dont appear

        //Kill the dentist list if a clicked on select btn
        Intent intent1 = new Intent("finish_promotion"); //for finish of patient_profile activity
        sendBroadcast(intent1);



        myProfile = (ImageView)findViewById(R.id.profile);
        name = (TextView)findViewById(R.id.dentist_name);
        phone = (TextView)findViewById(R.id.dentist_phone);
        address = (TextView)findViewById(R.id.dentist_address);
        email = (TextView)findViewById(R.id.dentist_email);
        patientImage = (ImageView)findViewById(R.id.dentist_image);

        appointment = (ImageView)findViewById(R.id.patient_appointment);
        logout_btn = (ImageView)findViewById(R.id.logout_btn);
        testProfile = (Button)findViewById(R.id.patient_dasboard_testProfile);

        services = (ImageView)findViewById(R.id.patient_services);

        treatmentPlan = (ImageView)findViewById(R.id.patient_treatmentPlan);

        wishlist = (ImageView)findViewById(R.id.patient_wishlist);

        promotions = (ImageView)findViewById(R.id.patient_promotion);

        testimonial = (ImageView)findViewById(R.id.patient_testimonial);

        rewards = (ImageView)findViewById(R.id.patient_reward);


        myDentist = (ImageView)findViewById(R.id.my_dentist);
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
                        patientImage.setImageBitmap(bm);
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


        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDashboard.this, PatientProfile.class);
                startActivity(intent);
            }
        });

        testProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDashboard.this, PatientProfile.class);
                startActivity(intent);
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });


        myDentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDashboard.this, DentistDashboard.class);
                startActivity(intent);
            }
        });


        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MyApplication.getsInstance().getAllAppointments() == null)
                {
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendAppointmentJsonRequest(String.valueOf(patientsDentist.getId()));

                }
                else
                {
                    Intent intent = new Intent(PatientDashboard.this, Appointments.class);
                    startActivity(intent);

                }


            }
        });



        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDashboard.this, Services.class);
                startActivity(intent);
            }
        });



        treatmentPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.getsInstance().getCurrentUser().getPatientTreatmentPlan() == null) {
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendTreatmentPlanJsonRequest(MyApplication.getsInstance().getCurrentUser().getId());
                }
                else
                {
                    Intent intent = new Intent(PatientDashboard.this, TreatmentPlan.class);
                    startActivity(intent);

                }



            }
        });



        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//              Always fetch json because wishlist can be removed added at runtime
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendWishlistJsonRequest(MyApplication.getsInstance().getCurrentUser().getId());



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
                    Intent intent = new Intent(PatientDashboard.this, Promotions.class);
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
                    Intent intent = new Intent(PatientDashboard.this, Testimonial.class);
                    startActivity(intent);
                }





            }
        });


        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistRewards() == null) {

                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendRewardJsonRequest(String.valueOf(patientsDentist.getId()));

                }
                else
                {
                    Intent intent = new Intent(PatientDashboard.this, Rewards.class);
                    startActivity(intent);
                }
            }
        });




    }

    public void sendAppointmentJsonRequest(String dentistId) {
        String url = getAppointmentRequestUrl(dentistId, Keys.Status.DENTIST_AVAL_APPOINTMENTS);
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
                        Log.v("MOULIA", "Appointment\n"+response);

                        if (response.equals("0")) {
                            Log.v("MOULIA", "No Appointments");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<AppointentModel> appointmentInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentAppointent = jsonArray.getJSONObject(i);
                                    String date;
                                    String time  =currentAppointent.getString("startDateTime");
                                    String[] split = time.split("T");
                                    time = split[1];
                                    date = split[0];



                                    AppointentModel info = new AppointentModel(
                                            currentAppointent.getString("title"),
                                            currentAppointent.getString("dentistId"),
                                            currentAppointent.getString("appointmentType"),
                                            currentAppointent.getString("Id"),
                                            currentAppointent.getString("startDateTime"),
                                            currentAppointent.getString("endDateTime"), time,date);

                                    appointmentInfos.add(info);

                                    Log.v("MOULIA", info.toString());
                                }

                                if (appointmentInfos != null) {
                                    MyApplication.getsInstance().setAllAppointments(appointmentInfos);
                                }

                                Intent intent = new Intent(PatientDashboard.this, Appointments.class);
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

    public String getAppointmentRequestUrl(String dentistId,int status) {
        // http://dentalvalet.com/api/app?dentistId=3&status=16 appointments
        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_DENTIST_ID+
                dentistId+
                Keys.URL_CHAR_AMPERSAND+
                Keys.URL_PARAM_STATUS +
                status;
    }


    public void sendTreatmentPlanJsonRequest(String patientId) {
        String url = getTreatmentPlanRequestUrl(patientId, Keys.Status.TREATMENT_PLAN);
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
                            Log.v("MOULIA", "No Appointments");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<TreatmentPlanModel> treatmentplanInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentTreatmentplan = jsonArray.getJSONObject(i);

                                    TreatmentPlanModel info = new TreatmentPlanModel(
                                            currentTreatmentplan.getString("PlanId"),
                                            currentTreatmentplan.getString("PlanTitle"),
                                            currentTreatmentplan.getString("description"),
                                            currentTreatmentplan.getString("cost"),
                                            currentTreatmentplan.getString("status"),
                                            currentTreatmentplan.getString("serviceTitle"),
                                            currentTreatmentplan.getString("servDescription"),
                                            currentTreatmentplan.getString("requiredHours"),
                                            currentTreatmentplan.getString("serviceCost"),
                                            currentTreatmentplan.getString("mandatory"),
                                            currentTreatmentplan.getString("serviceDate"),
                                            currentTreatmentplan.getString("HygienistTime"),
                                            currentTreatmentplan.getString("serviceId")

                                            );

                                    treatmentplanInfos.add(info);

                                }

                                if (treatmentplanInfos != null) {
                                    MyApplication.getsInstance().getCurrentUser().setPatientTreatmentPlan(treatmentplanInfos);
                                }

                                Intent intent = new Intent(PatientDashboard.this, TreatmentPlan.class);
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

    public String getTreatmentPlanRequestUrl(String patientId,int status) {
       // http://dentalvalet.com/api/app?patientId=4&status=22 treatment plan
        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_PATIENT_ID+
                patientId+
                Keys.URL_CHAR_AMPERSAND+
                Keys.URL_PARAM_STATUS +
                status;


    }




    public void sendWishlistJsonRequest(String patientId) {
        String url = getWishlistRequestUrl(patientId, Keys.Status.PATIENT_WISHLIST);
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
                            Log.v("MOULIA", "No Appointments");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<WishlistModel> wishlistInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentWishlist = jsonArray.getJSONObject(i);

                                    WishlistModel info = new WishlistModel(
                                            currentWishlist.getString("Id"),
                                            currentWishlist.getString("dentistId"),
                                            currentWishlist.getString("title"),
                                            currentWishlist.getString("dentistTime"),
                                            currentWishlist.getString("HygienistTime"),
                                            currentWishlist.getString("cost"),
                                            currentWishlist.getString("image")
                                    );

                                    wishlistInfos.add(info);

                                    Log.v("MOULIA", info.getDetistTime() + "  " + info.getHygienistTime());
                                }

                                if (wishlistInfos != null) {
                                    MyApplication.getsInstance().getCurrentUser().setPatientWishlist(wishlistInfos);
                                }

                                Intent intent = new Intent(PatientDashboard.this, WishList.class);
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

    public String getWishlistRequestUrl(String patientId,int status) {
        // http://dentalvalet.com/api/App?status=40&patientId=48
        return "http://dentalvalet.com/api/App" +
                Keys.URL_CHAR_QUESTION +
                Keys.URL_PARAM_STATUS +
                status+
                Keys.URL_CHAR_AMPERSAND+
                Keys.Attributes.KEY_PATIENT_ID+
                patientId;
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

                                Intent intent = new Intent(PatientDashboard.this, Promotions.class);
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

                                Intent intent = new Intent(PatientDashboard.this, Testimonial.class);
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


    public void sendRewardJsonRequest(String dentistId) {
        String url = getRewardRequestUrl(dentistId, Keys.Status.REWARD);
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
                            Log.v("MOULIA", "No Reward");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<RewardsModel> rewardInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentReward = jsonArray.getJSONObject(i);

                                    RewardsModel info = new RewardsModel(
                                            currentReward.getString("Id"),
                                            currentReward.getString("dentistId"),
                                            currentReward.getString("title"),
                                            currentReward.getString("description"),
                                            currentReward.getString("image"),
                                            currentReward.getString("scoreRequired"));

                                    rewardInfos.add(info);


                                }

                                if (rewardInfos!= null) {
                                    MyApplication.getsInstance().getCurrentUser().getSelectedDentist().setDentistRewards(rewardInfos);
                                }

                                Intent intent = new Intent(PatientDashboard.this, Rewards.class);
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

    public String getRewardRequestUrl(String dentistId,int status) {
        // http://dentalvalet.com/api/App?status=27&dentistId=3 reward
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
      //  moveTaskToBack(true);
    }


}
