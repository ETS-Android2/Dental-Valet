package com.dentalvalet.dentalvaletApp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_CHAR_AMPERSAND;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_CHAR_QUESTION;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_DENTAL_VALET_APP;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_PARAM_STATUS;

/**
 * Created by Awais Mahmood on 25-Nov-15.
 */
public class SearchDentist extends FragmentActivity {

    Button filter;
    private Spinner insurranceSpinner;
    private Spinner specialitySpinner;
    RequestQueue insurranceQueue;
    RequestQueue specialityQueue;
    ArrayAdapter<String> adapter;
    JSONArray insurranceJsonArray;
    JSONArray specialityJsonArray;
    RequestQueue queue;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String name;
    String insurance;
    String speciality;
    String zipcode;
    EditText nameText;
    EditText zipcodeText;
    ProgressDialog progressDialog;

    private static String[] insuranceArray = null;
    private static String[] specialityArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dentist);

        nameText = (EditText) findViewById(R.id.name);
        nameText.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (nameText.getText().toString().equals("Name")){
                    nameText.setText("");
                }
                return false;
            }
        });

        zipcodeText = (EditText) findViewById(R.id.zipcode);

        zipcodeText.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (zipcodeText.getText().toString().equals("Zip Code")){
                    zipcodeText.setText("");
                }
                return false;
            }
        });
        insurranceSpinner = (Spinner) findViewById(R.id.insurance);
        specialitySpinner = (Spinner) findViewById(R.id.speciality);


        filter = (Button) findViewById(R.id.filter_btn);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (zipcodeText != null) {
//                    zipcode = zipcodeText.getText().toString();
//                    zipcode = zipcode.replace(" ","");
//
//
//                } else {
//                    zipcode = "";
//                }
//
//                if (nameText != null) {
//                    name = nameText.getText().toString();
//                    name = name.replace(" ", "");
//
//
//                } else {
//                    name = "";
//                }

                zipcode = zipcodeText.getText().toString();
                name = nameText.getText().toString();

                insurance = insurance.replace(" ","");
                speciality = speciality.replace(" ","");
                if(insurance.equals("Insurance"))
                {
                    insurance="";
                }
                if(speciality.equals("Speciality"))
                {
                    speciality="";
                }
//                if(name.equals("Name"))
//                {
//                    name="";
//                }
//                if(zipcode.equals("Zip Code"))
//                {
//                    zipcode="";
//                }
                progressDialog = new ProgressDialog(SearchDentist.this, DEFAULT_KEYS_DISABLE);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Searching...");
                progressDialog.show();

                queue = VolleySingleton.getsInstance().getRequestQueue();
                sendSearchJsonRequest(name, zipcode, insurance, speciality, Keys.Status.SEARCH_DENTIST);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchDentist.this,
                android.R.layout.simple_spinner_item, MyApplication.getsInstance().getInsuranceArray());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insurranceSpinner.setAdapter(adapter);
        insurranceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SearchDentist.this, "Sleclted", Toast.LENGTH_SHORT).show();
                // Log.v("MOULIA", "Selected");
                insurance = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SearchDentist.this, "Selected Nothing", Toast.LENGTH_SHORT).show();
            }
        });


        ArrayAdapter<String> specialityadapter = new ArrayAdapter<String>(SearchDentist.this,
                android.R.layout.simple_spinner_item, MyApplication.getsInstance().getSpecialityArray());

        specialityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialitySpinner.setAdapter(specialityadapter);
        specialitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                speciality = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SearchDentist.this, "Selected Nothing", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void sendSearchJsonRequest(String name, String zipcode, String insurance, String speciality, int status) {
        String url = getDentistRequestUrl(name, zipcode, insurance, speciality, status);
        Log.v("MOULIA", "Search " + url);
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

                        try {
                            jsonArray   = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.v("MOULIA", response);

                        if (jsonArray.length()==0) {
                            Log.v("MOULIA", "Search Failed");
                            Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<DentistInfo> dentistInfos = new ArrayList<>();
                            try {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentDentist = jsonArray.getJSONObject(i);


                                    DentistInfo info = new DentistInfo(currentDentist.getLong("Id"),
                                            currentDentist.getString("name"),
                                            currentDentist.getString("email"),
                                            currentDentist.getString("phone"),
                                            currentDentist.getString("expertise"),
                                            currentDentist.getString("officeAddress"),
                                            currentDentist.getString("image"),
                                            currentDentist.getString("zipcode"),
                                            currentDentist.getString("state"),
                                            currentDentist.getString("subscriptionStatus"),
                                            currentDentist.getString("city"),
                                            currentDentist.getString("country"),
                                            currentDentist.getString("education"),
                                            currentDentist.getString("practiseInfo"),
                                            currentDentist.getString("staff"),
                                            currentDentist.getString("practiceName"),
                                            currentDentist.getString("uniqueInfo"),
                                            currentDentist.getString("speciality"),
                                            currentDentist.getString("insuranceAccepted"));

                                    dentistInfos.add(info);

                                    Log.v("MOULIA", info.toString());
                                }

                                if (dentistInfos != null) {
                                    MyApplication.getsInstance().setAllDentists(dentistInfos);
                                }

                               MyApplication.getsInstance().setSearchFilter(true);
                                    finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        progressDialog.dismiss();

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

    public String getDentistRequestUrl(String name, String zipcode, String insurance, String speciality, int status) {
        //search
        // http://dentalvalet.com/api/app?name=irfan&zipcode=12354&insuranc=insuranceAccepted&speciality=Speciality&status=10
        return URL_DENTAL_VALET_APP +
                URL_CHAR_QUESTION +
                Keys.Attributes.KEY_NAME+
                name+
                URL_CHAR_AMPERSAND +

                Keys.Attributes.KEY_ZIPCODE+
                zipcode+
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_INSURANCE+
                insurance+
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_SPECIALITY+
                speciality+
                URL_CHAR_AMPERSAND +
                URL_PARAM_STATUS +
                status;
    }


}
