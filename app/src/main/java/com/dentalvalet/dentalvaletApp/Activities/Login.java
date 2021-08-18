package com.dentalvalet.dentalvaletApp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistEducationModel;
import com.dentalvalet.dentalvaletApp.Model.DentistExperienceModel;
import com.dentalvalet.dentalvaletApp.Model.DentistStaffModel;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Model.User;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Awais Mahmood on 18-Nov-15.
 */
public class Login extends AppCompatActivity {
    Button loginBtn;
    ImageView backBtn;
    EditText password;
    EditText username;
    TextView forgot_password;
    String usernameText;
    String pwdText;
    RequestQueue queue;
    ProgressDialog progressDialog;
    JSONObject jsonObject;


    public Login() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);

            }
        });


//        backBtn = (ImageView) findViewById(R.id.back_btn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                return;
//            }
//        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Finally clicked login", Toast.LENGTH_SHORT).show();

                usernameText = username.getText().toString();
                pwdText = password.getText().toString();

                if (usernameText.equals("") || pwdText.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Username OR Password Missing",
                            Toast.LENGTH_LONG).show();

                } else if (usernameText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(usernameText).matches()) {
                    username.setError("Enter a valid email address");
                } else {
                    // Save new user data
                    username.setError(null);
                    progressDialog = new ProgressDialog(Login.this, DEFAULT_KEYS_DISABLE);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();


                    loginBtn.setEnabled(false);
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendLoginCredentialsJsonRequest(usernameText, pwdText);
                }

            }
        });

    }

    public void sendLoginCredentialsJsonRequest(String email, String pwd) {
        String url = getRequestUrl(email, pwd, 1);
        // Log.v("MOULIA", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseLoginCredentials(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.v("MOULIA", error.getMessage());
                //Toast.makeText(Login.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Login.this, "Check You Internet Connection", Toast.LENGTH_SHORT).show();
                loginBtn.setEnabled(true);
                progressDialog.dismiss();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getRequestUrl(String email, String pwd, int status) {
        //  http://staging.dentalvalet.com/api/app?email=admin1@gmail.com&password=admin&status=1
        // login URL
        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_EMAIL +
                email +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_PASSWORD +
                pwd +
                Keys.URL_CHAR_AMPERSAND +
                Keys.URL_PARAM_STATUS +
                status;
    }

    public void parseLoginCredentials(String response) {
        // Display the first 500 characters of the response string.
        // response = response.replace("\\", "");
        // response = response.replace("/", "");
        response = response.substring(1, response.length() - 1);
        Log.v("MOULIA", response);

        if (response.equals("0")) {
            //Log.v("MOULIA", "Login Failed");
            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
            loginBtn.setEnabled(true);
            progressDialog.dismiss();
        } else {
            //Ask for Dentist Data as welll
            Log.v("MOULIA", response);
            sendPatientInfoJsonRequest(response);
            loginBtn.setEnabled(true);
        }
    }


    public void sendInsuranceJsonRequest(int status) {
        String url = getRequestUrl(status);
        // Log.v("MOULIA", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        response = response.replace("\\", "");
                        response = response.replace("/", "");
                        response = response.replace("\"\"Freedom of Choice", "");
                        response = response.substring(1, response.length() - 1);
                        Log.v("MOULIA", response);

                        if (response.equals("0")) {
                        } else {
                            try {
                                JSONArray insurranceJsonArray = new JSONArray(response);
                                String[] insuranceArray = new String[insurranceJsonArray.length() + 1];//to set the default value at 0 index
                                insuranceArray[0] = "Insurance";
                                for (int i = 0; i < insurranceJsonArray.length(); i++) {
                                    JSONObject currentInsurance = insurranceJsonArray.getJSONObject(i);
                                    insuranceArray[i + 1] = currentInsurance.getString("name");
                                    Log.v("MOULIA", insuranceArray[i]);

                                }
                                MyApplication.getsInstance().setInsuranceArray(insuranceArray);
                                sendSpecialityJsonRequest(32);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.v("MOULIA", error.getMessage());
                //Toast.makeText(Login.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MyApplication.getAppContext(), "Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public void sendSpecialityJsonRequest(int status) {
        String url = getRequestUrl(status);

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        response = response.replace("\\", "");
                        response = response.replace("/", "");
                        response = response.replace("\"\"Freedom of Choice", "");
                        response = response.substring(1, response.length() - 1);
                        Log.v("MOULIA", response);

                        if (response.equals("0")) {
                        } else {
                            try {
                                JSONArray specialityJsonArray = new JSONArray(response);
                                String[] specialityArray = new String[specialityJsonArray.length() + 1]; //to provide default value in first index
                                specialityArray[0] = "Speciality";
                                for (int i = 0; i < specialityJsonArray.length(); i++) {
                                    JSONObject currentInsurance = specialityJsonArray.getJSONObject(i);
                                    specialityArray[i + 1] = currentInsurance.getString("name");
                                }

                                MyApplication.getsInstance().setSpecialityArray(specialityArray);
                                Toast.makeText(MyApplication.getAppContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(Login.this, ListDentist.class);
                                startActivity(intent);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.v("MOULIA", error.getMessage());
                //Toast.makeText(Login.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MyApplication.getAppContext(), "Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


    public String getRequestUrl(int status) {
        //insurance http://staging.dentalvalet.com/api/App?status=33
        //specialitites : http://staging.dentalvalet.com/api/App?status=32
        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.URL_PARAM_STATUS +
                status;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void sendDentistJsonRequest(int status) {
        String url = getDentistRequestUrl(status);
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
                            Log.v("MOULIA", "Search Failed");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<DentistInfo> dentistInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
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

                                    ArrayList<AppointentModel> appointmentInfos = new ArrayList<>();
                                    try {
                                        JSONArray jsonArray1 = new JSONArray(currentDentist.getString("AvailableAppointments"));
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject currentAppointent = jsonArray1.getJSONObject(j);
                                            String date;
                                            String time  =currentAppointent.getString("startDateTime");
                                            String[] split = time.split("T");
                                            time = split[1];
                                            date = split[0];



                                            AppointentModel info1 = new AppointentModel(
                                                    currentAppointent.getString("title"),
                                                    currentAppointent.getString("dentistId"),
                                                    currentAppointent.getString("appointmentType"),
                                                    currentAppointent.getString("Id"),
                                                    currentAppointent.getString("startDateTime"),
                                                    currentAppointent.getString("endDateTime"), time,date);

                                            appointmentInfos.add(info1);

                                        }

                                        if (appointmentInfos != null) {
                                            info.setAvailableAppointments(appointmentInfos);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    // fetching services

                                    ArrayList<ServicesModel> serviceInfos = new ArrayList<>();
                                    try {
                                        JSONArray jsonArray2 = new JSONArray(currentDentist.getString("Services"));
                                        for (int k = 0; k < jsonArray2.length(); k++) {
                                            JSONObject currentService = jsonArray2.getJSONObject(k);


                                            ServicesModel info2 = new ServicesModel(
                                                    currentService.getString("Id"),
                                                    currentService.getString("dentistId"),
                                                    currentService.getString("title"),
                                                    currentService.getString("description"),
                                                    currentService.getString("requiredHours"),
                                                    currentService.getString("cost"),
                                                    currentService.getString("image"),
                                                    currentService.getString("mandatory"),
                                                    currentService.getString("HygienistTime"),
                                                    currentService.getString("docment"),
                                                    currentService.getString("video")
                                            );
                                            serviceInfos.add(info2);


                                        }


                                        if (serviceInfos != null) {
                                            info.setAvailableServices(serviceInfos);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }











                                    dentistInfos.add(info);

                                    Log.v("MOULIA", info.toString());
                                }

                                if (dentistInfos != null) {
                                    MyApplication.getsInstance().setAllDentists(dentistInfos);
                                }

                                sendInsuranceJsonRequest(33);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApplication.getAppContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getDentistRequestUrl(int status) {
        //http://dentalvalet.com/api/app?status=7 // login URL
      //  http://staging.dentalvalet.com/api/app?name=irfan&zipcode=12354&insuranc=insuranceAccepted&speciality=Speciality&status=10
      // using search api with no parameters to fetch all dentist info
        return "http://dentalvalet.com/api/app?name=&zipcode=&insuranc=&speciality=&status=10";
    }


    public void sendPatientInfoJsonRequest(String patientId) {
        String url = getPatientInfoRequestUrl(patientId, Keys.Status.PATIENT_BY_ID);
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
                            Log.v("MOULIA", "Search Failed");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();

                            try {
                                JSONObject currentUser = new JSONObject(response);
                                String id = currentUser.getString("Id");
                                final String dentistId = currentUser.getString("dentistId");
                                String name = currentUser.getString("name");
                                String email = currentUser.getString("email");
                                String phone = currentUser.getString("phone");
                                String address = currentUser.getString("address");
                                String image = currentUser.getString("image");
                                String password = currentUser.getString("password");


                                User info = new User(id, name, email, phone, address, image, password,dentistId);


                                if (info != null) {
                                    MyApplication.getsInstance().setCurrentUser(info);
                                }
                                Log.v("MOULIA", "Dentsitid" + dentistId);

                                if (dentistId.equals("null") || dentistId.equals("")) {
                                    //It means dentist is not hired yet
                                    Log.v("MOULIA", "Dentsit Not Select");

                                    sendDentistJsonRequest(Keys.Status.GET_ALL_DENTIST);//for dentist list
                                } else {
                                    //dentist Hired already
                                    Log.v("MOULIA","Dentsit Already Hired Fetching it Now for pateintid="+ id+" dentistid= "+dentistId);

                                    String url = getDentistRequestUrl(Keys.Status.GET_ALL_DENTIST);
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
                                                        Log.v("MOULIA", "In sendPatientInfoJsonRequest failed");
                                                        //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                                                        ArrayList<DentistInfo> dentistInfos = new ArrayList<>();
                                                        try {
                                                            JSONArray jsonArray = new JSONArray(response);
                                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                                JSONObject currentDentist = jsonArray.getJSONObject(i);
                                                                if(currentDentist.getString("Id").equals(dentistId))
                                                                {
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
                                                                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(info);
                                                                    Log.v("MOULIA", "Selected Dentist Id is " + MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getId());
                                                                    break;
                                                                }

                                                            }

                                                            //fetching services before loading the patient dastboard
                                                            //and download all the jsons related to dentist appointent,services...

                                                            sendServiceJsonRequest(dentistId);


                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(MyApplication.getAppContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
                                            // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();

                                        }
                                    });
                                    // Add the request to the RequestQueue.
                                    queue.add(stringRequest);


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApplication.getAppContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getPatientInfoRequestUrl(String patientId, int status) {
        //http://dentalvalet.com/api/app?userId=4&status=3 pateint by ID
        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_USERID +
                patientId +
                Keys.URL_CHAR_AMPERSAND +
                Keys.URL_PARAM_STATUS +
                status;
    }



    public void sendServiceJsonRequest(final String dentistId) {
        String url = getServiceRequestUrl(dentistId, Keys.Status.SERVIC_DENTIST_BY_DENTIST_ID);
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
                            Log.v("MOULIA", "No Services");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<ServicesModel> serviceInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                String[] serviceArray = new String[jsonArray.length() + 1];//to set the default value at 0 index
                                serviceArray[0]="Services";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentService = jsonArray.getJSONObject(i);


                                    ServicesModel info = new ServicesModel(
                                            currentService.getString("Id"),
                                            currentService.getString("dentistId"),
                                            currentService.getString("title"),
                                            currentService.getString("description"),
                                            currentService.getString("requiredHours"),
                                            currentService.getString("cost"),
                                            currentService.getString("image"),
                                            currentService.getString("mandatory"),
                                            currentService.getString("HygienistTime"),
                                            currentService.getString("docment"),
                                            currentService.getString("video")
                                    );
                                    serviceArray[i + 1] = currentService.getString("title");
                                    serviceInfos.add(info);


                                }

                                if (serviceInfos != null) {
                                    MyApplication.getsInstance().setAllServices(serviceInfos);
                                }
                                if (serviceArray != null) { // to show in drop down of appointment request screen
                                    Log.v("MOULIA", "Service Array is"+serviceArray.toString());
                                    MyApplication.getsInstance().setAllServicesArray(serviceArray);
                                }


                                sendEducationJsonRequest(dentistId);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MyApplication.getAppContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getServiceRequestUrl(String dentistId, int status) {
        // http://staging.dentalvalet.com/api/app?dentistId=1&status=11 services
        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_DENTIST_ID +
                dentistId +
                Keys.URL_CHAR_AMPERSAND +
                Keys.URL_PARAM_STATUS +
                status;
    }


    public void sendEducationJsonRequest(final String dentistId) {
        String url = getEducationRequestUrl(dentistId, Keys.Status.DENTIST_EDUCATION);
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
                            Log.v("MOULIA", "No EDucation");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<DentistEducationModel> educationInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentEducation = jsonArray.getJSONObject(i);


                                    DentistEducationModel info = new DentistEducationModel(
                                            currentEducation.getString("id"),
                                            currentEducation.getString("degreeTitle"),
                                            currentEducation.getString("startingDate"),
                                            currentEducation.getString("endingDate"),
                                            currentEducation.getString("university"));

                                    educationInfos.add(info);

                                    Log.v("MOULIA", "Education: ="+info.getTitle() + info.getUniversity());
                                }

                                if (educationInfos != null) {
                                    MyApplication.getsInstance().getCurrentUser().getSelectedDentist().setDentistEducation(educationInfos);
                                }

                                sendExperienceJsonRequest(dentistId);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApplication.getAppContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getEducationRequestUrl(String dentistId,int status) {
        //http://dentalvalet.com/api/RegisterDentist?status=10&dentistId=3  education
        return "http://dentalvalet.com/api/RegisterDentist"+
                Keys.URL_CHAR_QUESTION +
                Keys.URL_PARAM_STATUS +
                status+
                Keys.URL_CHAR_AMPERSAND+
                Keys.Attributes.KEY_DENTIST_ID+
                dentistId;

    }


    public void sendExperienceJsonRequest(final String dentistId) {
        String url = getExperienceRequestUrl(dentistId, Keys.Status.DENTIST_EXPERIENCE);
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
                            Log.v("MOULIA", "No Experience");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<DentistExperienceModel> experienceInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentExperience = jsonArray.getJSONObject(i);


                                    DentistExperienceModel info = new DentistExperienceModel(
                                            currentExperience.getString("id"),
                                            currentExperience.getString("title"),
                                            currentExperience.getString("duration"),
                                            currentExperience.getString("isActive"));

                                    experienceInfos.add(info);

                                    Log.v("MOULIA", info.toString());
                                }

                                if (experienceInfos != null) {
                                    MyApplication.getsInstance().getCurrentUser().getSelectedDentist().setDentistExperience(experienceInfos);
                                }

                                sendStaffJsonRequest(dentistId);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApplication.getAppContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getExperienceRequestUrl(String dentistId,int status) {
        //http://dentalvalet.com/api/RegisterDentist?status=12&dentistId=3  experience

        return "http://dentalvalet.com/api/RegisterDentist"+
                Keys.URL_CHAR_QUESTION +
                Keys.URL_PARAM_STATUS +
                status+
                Keys.URL_CHAR_AMPERSAND+
                Keys.Attributes.KEY_DENTIST_ID+
                dentistId;

    }


    public void sendStaffJsonRequest(String dentistId) {
        String url = getStaffRequestUrl(dentistId, Keys.Status.DENTIST_STAFF);
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
                            Log.v("MOULIA", "No Experience");
                            //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                            ArrayList<DentistStaffModel> staffInfos = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentStaff = jsonArray.getJSONObject(i);


                                    DentistStaffModel info = new DentistStaffModel(
                                            currentStaff.getString("Id"),
                                            currentStaff.getString("dentistId"),
                                            currentStaff.getString("name"),
                                            currentStaff.getString("position"),
                                            currentStaff.getString("description"),
                                            currentStaff.getString("gender"),
                                            currentStaff.getString("image"));

                                    staffInfos.add(info);

                                }

                                if (staffInfos != null) {
                                    MyApplication.getsInstance().getCurrentUser().getSelectedDentist().setDentistStaff(staffInfos);
                                }

                                Intent intent = new Intent(Login.this, PatientDashboard.class);
                                startActivity(intent);
                                finish();
                                return;


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApplication.getAppContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getStaffRequestUrl(String dentistId,int status) {
        //http://dentalvalet.com/api/App?status=41&dentistId=3 dentist staff

        return "http://dentalvalet.com/api/App"+
                Keys.URL_CHAR_QUESTION +
                Keys.URL_PARAM_STATUS +
                status+
                Keys.URL_CHAR_AMPERSAND+
                Keys.Attributes.KEY_DENTIST_ID+
                dentistId;

    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onBackPressed() {
        finish();
        return;
    }


}
