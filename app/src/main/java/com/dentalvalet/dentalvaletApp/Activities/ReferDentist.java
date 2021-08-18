package com.dentalvalet.dentalvaletApp.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class ReferDentist extends FragmentActivity{


    EditText emai11;
    EditText emai12;
    EditText email3;
    EditText email4;
    Button cancel;
    Button referDentist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_dentist);

        referDentist = (Button) findViewById(R.id.refer_dentist_btn);
        referDentist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Finally clicked login", Toast.LENGTH_SHORT).show();

                emai11 = (EditText) findViewById(R.id.refer_email1);
                emai12 = (EditText) findViewById(R.id.refer_email2);
                email3 = (EditText) findViewById(R.id.refer_email3);
                email4 = (EditText) findViewById(R.id.refer_email4);

                String email1 = emai11.getText().toString();
                String email2 = emai12.getText().toString();
                String test = email3.getText().toString();
                String test1 = email4.getText().toString();

                if (email1.isEmpty() && email1.isEmpty() && email1.isEmpty() && email1.isEmpty() )
                {
                    emai11.setError("Enter email address");
                }
                else
                {
                    if (!email1.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                        emai11.setError("Enter a valid email address");

                    }
                    else
                    {
                        new AlertDialog.Builder(ReferDentist.this)
                                .setTitle("Success")
                                .setMessage("Dentist Refered Successfully.")
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


                                                Intent intent3 = new Intent("finish_testominoal"); //for finish of patient_profile activity
                                                sendBroadcast(intent3);


                                                Intent intent4 = new Intent("finish_TreatmentPlane"); //for finish of patient_profile activity
                                                sendBroadcast(intent4);

                                                Intent intent5 = new Intent("finish_wishlist"); //for finish of patient_profile activity
                                                sendBroadcast(intent5);

                                                Intent intent6 = new Intent("finish_activity"); //for finish of patient_profile activity
                                                sendBroadcast(intent6);



                                                finish();
                                                return;
                                            }
                                        })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if (!email2.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
                        emai12.setError("Enter a valid email address");

                    }
                    if (!test.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(test).matches()) {
                        email3.setError("Enter a valid email address");

                    }
                    if (!test1.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(test1).matches()) {
                        email4.setError("Enter a valid email address");
                    }



                }



            }
        });
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
