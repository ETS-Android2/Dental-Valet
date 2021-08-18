package com.dentalvalet.dentalvaletApp.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.MotionEvent;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Extras.MultipartServer;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awais Mahmood on 29-Nov-15.
 */
public class UpdatePatientProfile extends FragmentActivity {

    Button update_patient;
    ImageView backBtn;
    ImageView updateProfile;
    ImageView patientImage;
    EditText patientName;
    private ImageLoader imageLoader;
    EditText patientEmail;
    EditText patientPhone;
    EditText patientAddress;
    static final int RESULT_LOAD_IMG = 1;
    EditText patientPassword;
    RequestQueue queue;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "http://dentalvalet.com/api/FileUpload";
    String name, email, phone, address, image, password, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_patient_profile);
//        backBtn = (ImageView)findViewById(R.id.back_btn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                return;
//            }
//        });

        update_patient = (Button) findViewById(R.id.update_patient);
        update_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                queue = VolleySingleton.getsInstance().getRequestQueue();
                sendJsonRequest();

                Intent intent = new Intent("finish_activity"); //for finish of patient_profile activity
                sendBroadcast(intent);

                finish();
                return;
            }
        });


        imageLoader = VolleySingleton.getsInstance().getImageLoader();
        patientImage = (ImageView) findViewById(R.id.patient_profile_image);
        patientName = (EditText) findViewById(R.id.patient_profile_name);
        patientEmail = (EditText) findViewById(R.id.patieint_profile_email);
        patientPhone = (EditText) findViewById(R.id.patieint_profile_phone);
        patientAddress = (EditText) findViewById(R.id.patieint_profile_address);
        patientPassword = (EditText) findViewById(R.id.patieint_profile_password);

        name = MyApplication.getsInstance().getCurrentUser().getName();
        email = MyApplication.getsInstance().getCurrentUser().getEmail();
        phone = MyApplication.getsInstance().getCurrentUser().getPhone();
        address = MyApplication.getsInstance().getCurrentUser().getAddress();
        image = MyApplication.getsInstance().getCurrentUser().getImage();
        password = MyApplication.getsInstance().getCurrentUser().getPassword();
        id = MyApplication.getsInstance().getCurrentUser().getId();

        patientName.setText(name);
        patientEmail.setText(email);
        patientPhone.setText(phone);
        patientAddress.setText(address);
        patientPassword.setText(password);

        String imageUrl = Keys.URL_PATIENT_IMAGES + image;
        if (imageUrl != null) {
            Log.v("MOULIA", "Image Update" + imageUrl);
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
                        Bitmap bm = MyApplication.getsInstance().getCircleBitmap(response.getBitmap());
                        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.profile_patient_white_box, null);
                        int h = d.getIntrinsicHeight() - 50;
                        int w = d.getIntrinsicWidth() - 50;
                        bm = Bitmap.createScaledBitmap(bm, h, w, true);
                        patientImage.setImageBitmap(bm);
                    } else {
                        //set Some loader image here
                    }

                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }


        patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });


    }


    public void sendJsonRequest() {


        String url = getRequestUrl(id, patientName.getText().toString(), patientEmail.getText().toString(), patientPhone.getText().toString(), patientAddress.getText().toString(), patientPassword.getText().toString(), image, Keys.Status.UPDATE_PATIENT);
        Log.v("MOULIA", "Update " + url);

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
                Toast.makeText(UpdatePatientProfile.this, "Check You Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public String getRequestUrl(String id, String name, String email, String phone, String address, String password, String image, int status) {
        //  http://staging.dentalvalet.com/api/App?Id=1&name=irfan&email=irfan@yahoo.com&address=252525&phone=525525&password=5252525&image=imagename&status=26
        return "http://dentalvalet.com/api/App" +
                Keys.URL_CHAR_QUESTION +
                Keys.Attributes.KEY_ID +
                id +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_NAME +
                android.net.Uri.encode(name, "UTF-8") +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_EMAIL +
                email +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_ADDRESS +
                android.net.Uri.encode(address, "UTF-8") +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_PHONE +
                phone +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_PASSWORD +
                password +
                Keys.URL_CHAR_AMPERSAND +
                Keys.Attributes.KEY_IMAGE +
                image +
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
        if (response.equals("Updated")) {
            Toast.makeText(UpdatePatientProfile.this, response, Toast.LENGTH_SHORT).show();

            String name = patientName.getText().toString();
            name = name.replace(" ", "");
            String email = patientEmail.getText().toString();
            email = email.replace(" ", "");
            String phone = patientPhone.getText().toString();
            phone = phone.replace(" ", "");
            String address = patientAddress.getText().toString();
            address = address.replace(" ", "");
            String pass = patientPassword.getText().toString();
            pass = pass.replace(" ", "");


            MyApplication.getsInstance().getCurrentUser().setName(name);
            MyApplication.getsInstance().getCurrentUser().setEmail(email);
            MyApplication.getsInstance().getCurrentUser().setPhone(phone);
            MyApplication.getsInstance().getCurrentUser().setAddress(address);
            MyApplication.getsInstance().getCurrentUser().setImage(image);
            MyApplication.getsInstance().getCurrentUser().setPassword(pass);
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
        finish();
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();


                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                Bitmap bm = MyApplication.getsInstance().getCircleBitmap(BitmapFactory.decodeFile(imgDecodableString));
                photo=BitmapFactory.decodeFile(imgDecodableString);
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.profile_patient_white_box, null);
                int h = d.getIntrinsicHeight() - 50;
                int w = d.getIntrinsicWidth() - 50;
                bm = Bitmap.createScaledBitmap(bm, h, w, true);
                patientImage.setImageBitmap(bm);


                Log.v("MOULIA", "Imae path" + picturePath);
                Log.v("MOULIA", "Picture path" + Environment.getExternalStorageDirectory().toString());
                new RetrieveFeedTask(picturePath, BitmapFactory.decodeFile(imgDecodableString)).execute();


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }




    class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {

        private Exception exception;
        String path;
        Bitmap image;

        public RetrieveFeedTask(String path, Bitmap image) {
            this.path = path;
            this.image = image;
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("UploadedImage", path));
            URL url;
            try {
                url =  new URL("http://dentalvalet.com/api/FileUpload");
                MultipartServer.postData(url, nameValuePairs);
            } catch (MalformedURLException e) {
                Log.v("MOULIA", "Exception");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("MOULIA", "Exception");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(UpdatePatientProfile.this, "Image uploaded", Toast.LENGTH_SHORT).show();
        }


    }


}
