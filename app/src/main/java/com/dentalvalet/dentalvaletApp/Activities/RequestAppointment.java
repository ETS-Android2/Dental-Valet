package com.dentalvalet.dentalvaletApp.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 01-Dec-15.
 */
public class RequestAppointment extends FragmentActivity {

    TextView name;
    TextView phone;
    TextView address;
    TextView email;
    ImageView dentistImage;
    DentistInfo patientsDentist;
    AppointentModel selectedAppointment;
    private ImageLoader imageLoader;
    ImageView backBtn;
    TextView date;
    TextView time;
    Button dashboard_btn;
    Button request_appointment;
    RequestQueue queue;
    String startDate;
    EditText descp;
    private Spinner servicesSpinner;
    String selectedService;
    ProgressDialog progressDialog;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_appointment);

        date = (TextView)findViewById(R.id.date);
        time = (TextView)findViewById(R.id.time);
        name = (TextView)findViewById(R.id.dentist_name);
        phone = (TextView)findViewById(R.id.dentist_phone);
        address = (TextView)findViewById(R.id.dentist_address);
        email = (TextView)findViewById(R.id.dentist_email);
        dentistImage = (ImageView)findViewById(R.id.dentist_image);
        dashboard_btn = (Button)findViewById(R.id.dashboard_btn);
        request_appointment = (Button)findViewById(R.id.appointment_request);
                descp = (EditText)findViewById(R.id.request_appointment_description);
        servicesSpinner = (Spinner)findViewById(R.id.request_service);

        if(MyApplication.getsInstance().getCurrentUser().getSelectedDentist()!=null)
        {
            patientsDentist =MyApplication.getsInstance().getCurrentUser().getSelectedDentist();
        }

        if( MyApplication.getsInstance().getCurrentUser().getSelectedAppointment()!=null)
        {
            selectedAppointment  = MyApplication.getsInstance().getCurrentUser().getSelectedAppointment();
        }

        name.setText(patientsDentist.getName());
        phone.setText(patientsDentist.getMobile());
        address.setText(patientsDentist.getOfficeAddress());
        email.setText(patientsDentist.getEmail());
        date.setText(selectedAppointment.getDate());
        time.setText(selectedAppointment.getTime());

         startDate = selectedAppointment.getDate()+"T"+selectedAppointment.getTime();
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

          ArrayAdapter<String> adapter = new ArrayAdapter<String>(RequestAppointment.this,
                android.R.layout.simple_spinner_item, MyApplication.getsInstance().getAllServicesArray());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(adapter);
        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SearchDentist.this, "Sleclted", Toast.LENGTH_SHORT).show();
                // Log.v("MOULIA", "Selected");
                selectedService = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(RequestAppointment.this, "No service Selected", Toast.LENGTH_SHORT).show();
            }
        });

        dashboard_btn.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                // if its coming from appointent screen jst finish both and dashboard appears
                    if (MyApplication.getsInstance().getCurrentUser().getHiredDentistStatus().equals("null")) {

                        new AlertDialog.Builder(RequestAppointment.this)
                                .setTitle("Causion")
                                .setMessage("Request Appointment first to hire a dentist.")
                                .setCancelable(true)
                                .setNeutralButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                            }
                                        })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    else {
                        Intent intent = new Intent("finish_appointment"); //for finish of patient_profile activity
                        sendBroadcast(intent);
                        Intent intent1 = new Intent("finish_promotion"); //for finish of patient_profile activity
                        sendBroadcast(intent1);
                        Intent intent2 = new Intent("finish_services"); //for finish of patient_profile activity
                        sendBroadcast(intent2);
                        finish();
                        return;

                    }

                }
            }

            );

            request_appointment.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                // if its coming from appointent screen jst finish both and dashboard appears
                    progressDialog = new ProgressDialog(RequestAppointment.this, DEFAULT_KEYS_DISABLE);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Sending...");
                    progressDialog.show();
                queue = VolleySingleton.getsInstance().getRequestQueue();
                sendRequestAppointmentJsonRequest(MyApplication.getsInstance().getCurrentUser().getId(), String.valueOf(patientsDentist.getId()), descp.getText().toString(), startDate, Keys.Status.REQUEST_APPOINTMENT);

            }
            }

            );


        }

    public void sendRequestAppointmentJsonRequest(String patientId,String dentistId,String reason,String startDate, int status) {
        String url = getRequestAppointmentUrl(patientId,dentistId,reason,startDate,status);
        Log.v("MOULIA","Request Appointment URL "+ url);

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
                Toast.makeText(RequestAppointment.this, "Check You Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
       queue.add(stringRequest);


    }

    public String getRequestAppointmentUrl(String patientId,String dentistId,String reason,String startDate, int status) {
        // http://staging.dentalvalet.com/api/App?patientId=1&dentistId=1&reason=testing%20from%20browser&startDate=2015-01-01T01:30:00&status=23 request appointment
        return "http://dentalvalet.com/api/App" +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_PATIENT_ID+
                patientId +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_DENTIST_ID +
                dentistId+
                Keys.URL_CHAR_AMPERSAND +
                "reason="+
                android.net.Uri.encode(reason, "UTF-8") +
                Keys.URL_CHAR_AMPERSAND +
                "startDate=" +
                startDate+
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
            // retry sending the request
            new AlertDialog.Builder(RequestAppointment.this)
                    .setTitle("Failed")
                    .setMessage("Retry sending your appointment request.")
                    .setCancelable(true)
                    .setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            //it will return the appointment Id



            if (MyApplication.getsInstance().getCurrentUser().getHiredDentistStatus().equals("null")) {

                //It means dentist is not hired yet

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(RequestAppointment.this)
                                .setTitle("Success")
                                .setMessage("Your Appointment request has been sent to Dentist.")
                                .setCancelable(true)
                                .setNeutralButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();

                                                Intent intent3 = new Intent("finish_appointment"); //for finish of patient_profile activity
                                                sendBroadcast(intent3);

                                                Intent intent4 = new Intent("finish_dentist_list"); //for finish of patient_profile activity
                                                sendBroadcast(intent4);


                                                Intent intent1 = new Intent("finish_promotion"); //for finish of patient_profile activity
                                                sendBroadcast(intent1);
                                                Intent intent2 = new Intent("finish_services"); //for finish of patient_profile activity
                                                sendBroadcast(intent2);

                                                Intent intent = new Intent(RequestAppointment.this, PatientDashboard.class);
                                                startActivity(intent);
                                                finish();
                                                return;
                                            }
                                        })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                }, 3000);  // 3000 milliseconds



            }
            else {

                //It means dentist is not hired yet
                progressDialog.dismiss();

                new AlertDialog.Builder(RequestAppointment.this)
                        .setTitle("Success")
                        .setMessage("Your Appointment request has been sent to Dentist.")
                        .setCancelable(true)
                        .setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                        Intent intent = new Intent("finish_appointment"); //for finish of patient_profile activity
                                        sendBroadcast(intent);

                                        Intent intent1 = new Intent("finish_promotion"); //for finish of patient_profile activity
                                        sendBroadcast(intent1);
                                        Intent intent2 = new Intent("finish_services"); //for finish of patient_profile activity
                                        sendBroadcast(intent2);
                                        finish();
                                        return;
                                    }
                                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }




        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
        finish();
        return;

    }
}
