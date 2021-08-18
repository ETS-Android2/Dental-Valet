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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class PatientProfile extends FragmentActivity {

    ImageView backBtn;
    ImageView updateProfile;
    ImageView patientImage;
    TextView patientName;
    private ImageLoader imageLoader;
    TextView patientEmail;
    TextView patientPhone;
    TextView patientAddress;
    ProgressBar progress;
    Button testUpdate;

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_profile);
        imageLoader = VolleySingleton.getsInstance().getImageLoader();
        patientImage = (ImageView) findViewById(R.id.patient_profile_image);
        patientName = (TextView) findViewById(R.id.patient_profile_name);
        patientEmail = (TextView) findViewById(R.id.patieint_profile_email);
        patientPhone = (TextView) findViewById(R.id.patieint_profile_phone);
        patientAddress = (TextView) findViewById(R.id.patieint_profile_address);
        progress = (ProgressBar) findViewById(R.id.patient_profile_progress);
        testUpdate = (Button) findViewById(R.id.patieint_profile_testUpdate);



        updateProfile = (ImageView)findViewById(R.id.update);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfile.this, UpdatePatientProfile.class);
                startActivity(intent);
            }
        });


        testUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfile.this, UpdatePatientProfile.class);
                startActivity(intent);
            }
        });


//        backBtn = (ImageView)findViewById(R.id.back_btn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                return;
//            }
//        });


        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_activity"));

        queue = VolleySingleton.getsInstance().getRequestQueue();
        sendJsonRequest(MyApplication.getsInstance().getCurrentUser().getId(), Keys.Status.PATIENT_BY_ID);



    }

    public void sendJsonRequest(String userId,int status) {
        String url = getRequestUrl(userId,status);
        // Log.v("MOULIA", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJSONResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.v("MOULIA", error.getMessage());
                //Toast.makeText(Login.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(PatientProfile.this, "Check You Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getRequestUrl(String userId, int status) {
       // http://dentalvalet.com/api/app?userId=48&status=15
        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_USERID +
                userId +
                Keys.URL_CHAR_AMPERSAND +
                Keys.URL_PARAM_STATUS +
                status;
    }

    public void parseJSONResponse(String response) throws JSONException {
        // Display the first 500 characters of the response string.
         response = response.replace("\\", "");
         response = response.replace("/", "");
        response = response.substring(1, response.length() - 1);
        //Log.v("MOULIA", response);

        if (response.equals("0")) {
            Toast.makeText(PatientProfile.this, response, Toast.LENGTH_SHORT).show();
        } else {
            //Ask for Dentist Data as welll
            Log.v("MOULIA", response);
            JSONObject currentUser = new JSONObject(response);
            String name = currentUser.getString("name");
            String email = currentUser.getString("email");
            String phone = currentUser.getString("phone");
            String address = currentUser.getString("address");
            String image = currentUser.getString("image");
            String password = currentUser.getString("password");

            MyApplication.getsInstance().getCurrentUser().setName(name);
            MyApplication.getsInstance().getCurrentUser().setPassword(password);
            MyApplication.getsInstance().getCurrentUser().setEmail(email);
            MyApplication.getsInstance().getCurrentUser().setPhone(phone);
            MyApplication.getsInstance().getCurrentUser().setAddress(address);
            MyApplication.getsInstance().getCurrentUser().setImage(image);

          patientName.setText(name);
            patientEmail.setText(email);
            patientPhone.setText(phone);
            patientAddress.setText(address);


            String imageUrl = Keys.URL_PATIENT_IMAGES+image;
            if(imageUrl!=null)
            {
                Log.v("MOULIA", imageUrl);
                imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        if(response.getBitmap() !=null)
                        {
                            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.profile_patient_white_box, null);
                            int h = d.getIntrinsicHeight();
                            int w = d.getIntrinsicWidth();
                            Bitmap bm =  Bitmap.createScaledBitmap(response.getBitmap(), h, w, true);
                            patientImage.setImageBitmap(bm);
                            progress.setVisibility(View.GONE);
                            patientImage.setVisibility(View.VISIBLE);
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


    }

    @Override
    public void onBackPressed() {
       finish();
        return;
    }


}
