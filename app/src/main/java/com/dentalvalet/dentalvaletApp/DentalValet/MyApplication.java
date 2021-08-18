package com.dentalvalet.dentalvaletApp.DentalValet;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.util.Log;

import com.dentalvalet.dentalvaletApp.Adaptors.DentistListviewAdaptor;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 21-Nov-15.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;
    private ArrayList<DentistInfo> allDentists;
    private ArrayList<AppointentModel> allAppointments;
    private ArrayList<ServicesModel> allServices;
    String[] insuranceArray;
    String[] specialityArray;
    private DentistListviewAdaptor adaptor; //to update the recycler list
    private boolean searchFilter = false;
    private User currentUser;
    String[] allServicesArray;

    public ArrayList<ServicesModel> getAllServices() {
        return allServices;
    }

    public void setAllServices(ArrayList<ServicesModel> allServices) {
        this.allServices = allServices;
    }

    public ArrayList<AppointentModel> getAllAppointments() {
        return allAppointments;
    }

    public void setAllAppointments(ArrayList<AppointentModel> allAppointments) {
        this.allAppointments = allAppointments;
    }

    public String[] getAllServicesArray() {
        return allServicesArray;
    }

    public void setAllServicesArray(String[] allServicesArray) {
        this.allServicesArray = allServicesArray;
    }

    public boolean isSearchFilter() {
        return searchFilter;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setSearchFilter(boolean searchFilter) {
        this.searchFilter = searchFilter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public DentistListviewAdaptor getAdaptor() {
        return adaptor;
    }

    public void setAdaptor(DentistListviewAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    public ArrayList<DentistInfo> getAllDentists() {
        return allDentists;
    }

    public void setAllDentists(ArrayList<DentistInfo> allDentists) {
        this.allDentists = allDentists;
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }


    public void hashKeyForFB() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.dentalvalet.www.dentalvalet",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = Color.RED;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

       // bitmap.recycle(); // recycler view has to use the recyle views so dont recyle the bitmap

        return output;
    }

    public void storeSharedPrefs() {
        /*
         * Storing in Shared Preferences
         */
        SharedPreferences settings = getSharedPreferences("settings", 0); // 0 - for private mode

        SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
        editor.commit();
    }

    public boolean getSharedPrefs() {
        SharedPreferences settings = getSharedPreferences("settings", 0);
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        return hasLoggedIn;

    }

    public String[] getInsuranceArray() {
        return insuranceArray;
    }

    public void setInsuranceArray(String[] insuranceArray) {
        this.insuranceArray = insuranceArray;
    }

    public String[] getSpecialityArray() {
        return specialityArray;
    }

    public void setSpecialityArray(String[] specialityArray) {
        this.specialityArray = specialityArray;
    }


}
