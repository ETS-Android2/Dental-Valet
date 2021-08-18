package com.dentalvalet.dentalvaletApp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class ForgotPassword extends FragmentActivity {

    ImageView backBtn;
    EditText email;
    RequestQueue queue;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        email = (EditText) findViewById(R.id.email);
        send = (Button) findViewById(R.id.send_btn);
//        backBtn = (ImageView) findViewById(R.id.back_btn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                return;
//            }
//        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email != null) {
                    String emailText = email.getText().toString();

                    if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                        email.setError("Enter a valid email address");
                    } else {
                        email.setError(null);

                        queue = VolleySingleton.getsInstance().getRequestQueue();
                        sendJsonRequest(emailText);
                    }
                }

            }
        });

    }


    public void sendJsonRequest(String email) {
        String url = getRequestUrl(email, Keys.Status.FORGOT_PASSWORD);
        Log.v("MOULIA", url);

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
                //Log.v("MOULIA", error.getMessage());
                //Toast.makeText(Login.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ForgotPassword.this, "Enter Valid Email Address Or Check You Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getRequestUrl(String email, int status) {
        // http://staging.dentalvalet.com/api/App?status=37&email=scott@dentalvalet.com

        return Keys.URL_DENTAL_VALET_APP +
                Keys.URL_CHAR_QUESTION +
                Keys.URL_PARAM_STATUS +
                status +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_EMAIL +
                email;

    }

    public void parseJSONResponse(String response) {
        // Display the first 500 characters of the response string.
        // response = response.replace("\\", "");
        // response = response.replace("/", "");
        response = response.substring(1, response.length() - 1);
        //Log.v("MOULIA", response);

        Toast.makeText(ForgotPassword.this, response, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onBackPressed() {
        finish();
        return;
    }

}
