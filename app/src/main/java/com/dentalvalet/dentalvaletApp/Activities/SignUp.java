package com.dentalvalet.dentalvaletApp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Model.User;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
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
 * Created by Awais Mahmood on 20-Nov-15.
 */
public class SignUp extends FragmentActivity {


    ImageView backBtn;
    Button signUpBtn;
    EditText username;
    EditText email;
    EditText pwd;
    EditText confirmPwd;

    String usrText;
    String emailText;
    String signupPwdText;
    String signupPwdCfrmText;
    String facebookId = "0";

    ProgressDialog progressDialog;
    RequestQueue queue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

//        backBtn = (ImageView)findViewById(R.id.back_btn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                return;
//            }
//        });

        username = (EditText) findViewById(R.id.contact_name);
        email = (EditText) findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.signup_pwd);
        confirmPwd = (EditText) findViewById(R.id.cfrm_pwd);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        confirmPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        // Checking If any parameters passed in case of facebook login
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            facebookId = extras.getString("fid");
            String nameWithoutSpcae = extras.getString("name").replace(" ", "");
            username.setText(nameWithoutSpcae);
            email.setText(extras.getString("email"));
        } else {
            facebookId = "0";
        }

        signUpBtn = (Button) findViewById(R.id.sign_up_btn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Finally clicked login", Toast.LENGTH_SHORT).show();
                usrText = username.getText().toString();
                emailText = email.getText().toString();
                signupPwdText = pwd.getText().toString();
                signupPwdCfrmText = confirmPwd.getText().toString();
                boolean flag = false;
                if (usrText.equals("") || signupPwdText.equals("") || signupPwdCfrmText.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the SIGN UP form",
                            Toast.LENGTH_LONG).show();
                    flag = false;


                } else {
                    flag = true;

                }
                if (signupPwdText.equals(signupPwdCfrmText) && flag == true) {
                    // Save new user data

                    progressDialog = new ProgressDialog(SignUp.this, DEFAULT_KEYS_DISABLE);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Signing In...");
                    progressDialog.show();

                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendJsonRequest(usrText, emailText, signupPwdText, signupPwdCfrmText);


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Password Dont Match",
                            Toast.LENGTH_LONG).show();

                }


            }
        });
    }

    public void sendJsonRequest(String username, String pwd, String email, String pwdConfirm) {
        String url = getRequestUrl(username, email, pwd, pwdConfirm, 2);
        Log.v("MOULIA", "SignupURL "+url);

        // Request a string response from the provided URL.
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSONResponse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUp.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUp.this, "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getRequestUrl(String username, String pwd, String email, String pwdConfirm, int status) {
        //Registration
        // http://dentalvalet.com/api/app?name=IrfanSaeeMalik&email=irfa@yahoo.com&password=irf&gender=male&birthday=01/12/1990&fbId=0&tId=0&phone=03453177413&address=Gulberg&status=2// login URL
        return URL_DENTAL_VALET_APP +
                URL_CHAR_QUESTION +
                Keys.Attributes.KEY_NAME +
                username +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_EMAIL +
                email +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_PASSWORD +
                pwd +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_GENDER +
                "male" +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_DOB +
                "" +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_FBID +
                0 +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_TID +
                0 +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_PHONE +
                "" +
                URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_ADDRESS +
                "" +
                URL_CHAR_AMPERSAND +
                URL_PARAM_STATUS +
                status;

    }

    public void parseJSONResponse(String response) {
        // Display the first 500 characters of the response string.
        response = response.replace("\\", "");
        response = response.replace("/", "");
        response = response.substring(1, response.length() - 1);
        //  Log.v("MOULIA", response);
        //Integer val = Integer.valueOf(response);
          Log.v("MOULIA", response.toString());
        if (response.equals("0")) {
            //   Log.v("MOULIA", "SignUp Failed");
            Toast.makeText(SignUp.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

        } else {
            Toast.makeText(SignUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
            sendPatientInfoJsonRequest(response);
//            Intent intent = new Intent(MyApplication.getAppContext(), ListDentist.class);
//            startActivity(intent);


        }
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
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApplication.getAppContext(), "Connection time out", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();


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
                Toast.makeText(MyApplication.getAppContext(), "Connection time out", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();// Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getDentistRequestUrl(int status) {
        //http://dentalvalet.com/api/app?status=7 // login URL
        // return "http://dentalvalet.com/api/app?name=&zipcode=&insuranc=&speciality=&status=10";
//        return Keys.URL_DENTAL_VALET_APP +
//                Keys.URL_CHAR_QUESTION +
//                Keys.URL_PARAM_STATUS +
//                status;
        return "http://dentalvalet.com/api/app?name=&zipcode=&insuranc=&speciality=&status=10";
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
                                progressDialog.dismiss();
                                Intent intent = new Intent(SignUp.this, ListDentist.class);
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
