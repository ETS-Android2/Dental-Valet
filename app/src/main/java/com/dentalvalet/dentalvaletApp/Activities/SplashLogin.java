package com.dentalvalet.dentalvaletApp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Model.User;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.www.dentalvaletApp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_CHAR_AMPERSAND;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_CHAR_QUESTION;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_DENTAL_VALET_APP;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_PARAM_STATUS;


/**
 * Created by Awais Mahmood on 19-Nov-15.
 */
public class SplashLogin extends AppCompatActivity {

    RequestQueue queue;
    JSONObject jsonObject;
    JSONObject faceBookResponse;
    Button fbBtn;
    Button cntnue;
    private TextView textView;
    private CallbackManager callbackManager;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

//            if(profile!=null)
//            {
//
//                textView.setText("Welcomeee  " + profile.getName() + " URI: " +profile.getLinkUri()+" ID "+profile.getId());
//                insurranceQueue = VolleySingleton.getsInstance().getRequestQueue();
//              sendLoginCredentialsJsonRequest(profile.getId().toString());//passing FBID
//
//            }

            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code

                          //  Log.v("MOULIA", "FACEBOOK "+ object.toString());
                            try {
                                faceBookResponse = object;
                                textView.setText("Welcomeee  " + object.getString("email").toString());
                                queue = VolleySingleton.getsInstance().getRequestQueue();
                                sendJsonRequest(object.getString("id").toString());//passing FBID
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,email");
            request.setParameters(parameters);
            request.executeAsync();

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            Log.v("MOULIA", error.getMessage());
            Toast.makeText(SplashLogin.this, "Login Failed \n "+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.splash_login);
        textView = (TextView) findViewById(R.id.welcome);
        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions("email");
        fbBtn = (Button) findViewById(R.id.facebook_splash_btn);
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(
                        SplashLogin.this,
                        Arrays.asList("email"));
                loginButton.registerCallback(callbackManager, callback);
                loginButton.performClick();

            }
        });

        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.lock);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Finally clicked login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashLogin.this, Login.class);
                startActivity(intent);
            }
        });


        Button signup = (Button) findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Finally clicked login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashLogin.this, SignUp.class);
                startActivity(intent);
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void sendJsonRequest(String facebookId) {
        String url = getRequestUrl(facebookId, 111);//for facebook ID //956676191034068 usama
        Log.v("MOULIA", "FACEBOOK "+url);

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
                Toast.makeText(SplashLogin.this, "Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getRequestUrl(String faceBookId, int status) {
        // http://dentalvalet.com/api/App?fbId=100000536836757&status=111
        // login URL
        return URL_DENTAL_VALET_APP +
                URL_CHAR_QUESTION +
                Keys.Attributes.KEY_FBID +
                faceBookId +
                URL_CHAR_AMPERSAND +
                URL_PARAM_STATUS +
                status;
    }

    public void parseJSONResponse(String response) throws JSONException {
        // Display the first 500 characters of the response string.
        response = response.replace("\\", "");
        response = response.replace("/", "");
        response = response.substring(1, response.length() - 1);
        Log.v("MOULIA", response);

        if (response.equals("0")) {

            //No facebook Id
            Log.v("MOULIA", "Not In Database with this FBID");
            Toast.makeText(MyApplication.getAppContext(), "SignUp From Facebook", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyApplication.getAppContext(), SignUp.class);

            intent.putExtra("fid", faceBookResponse.getString("id"));//facebook id
            intent.putExtra("email", faceBookResponse.getString("email"));
            intent.putExtra("name", faceBookResponse.getString("name"));
            String name = faceBookResponse.getString("name").replace(" ","");

            String url = getSignUpUrl(faceBookResponse.getString("id"), name, "123", faceBookResponse.getString("email"), "123", 2);
            Log.v("MOULIA", "FACEBOOK URL"+ url);

            // Request a string response from the provided URL.
            StringRequest stringRequest;
            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response = response.replace("\\", "");
                            response = response.replace("/", "");
                            response = response.substring(1, response.length() - 1);
                            //  Log.v("MOULIA", response);
                            //Integer val = Integer.valueOf(response);
                              Log.v("MOULIA", "FACEBOOK SIGNUP "+response);
                            if (response.equals("0")) {
                                //   Log.v("MOULIA", "SignUp Failed");
                                Toast.makeText(MyApplication.getAppContext(), "Facebook SignUp Failed", Toast.LENGTH_SHORT).show();


                            } else {

                                JSONObject currentUser = null;
                                try {
                                    currentUser = new JSONObject(response);
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
                                    Log.v("MOULIA", "FacebookDentsitid" + dentistId);

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
                    Toast.makeText(MyApplication.getAppContext(), "Check You Internet Connection", Toast.LENGTH_SHORT).show();
                    Log.v("MOULIA", error.getMessage());
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);



            //startActivity(intent);
        } else {

            //response as a patient object In it
            JSONObject currentUser = null;
            try {
                currentUser = new JSONObject(response);
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
                Log.v("MOULIA", "FacebookDentsitid" + dentistId);

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

    public String getSignUpUrl(String FbId, String username, String pwd, String email, String pwdConfirm, int status) {
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
                FbId +
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
                Log.v("MOULIA", error.getMessage());
                Toast.makeText(MyApplication.getAppContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(MyApplication.getAppContext(), "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getDentistRequestUrl(int status) {
        //http://dentalvalet.com/api/app?status=7 // login URL
//        return Keys.URL_DENTAL_VALET_APP +
//                Keys.URL_CHAR_QUESTION +
//                Keys.URL_PARAM_STATUS +
//                status;
        //http://dentalvalet.com/api/app?status=7 // login URL
        //  http://staging.dentalvalet.com/api/app?name=irfan&zipcode=12354&insuranc=insuranceAccepted&speciality=Speciality&status=10
        // using search api with no parameters to fetch all dentist info
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
                                Toast.makeText(MyApplication.getAppContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SplashLogin.this, ListDentist.class);
                                startActivity(intent);

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




}
