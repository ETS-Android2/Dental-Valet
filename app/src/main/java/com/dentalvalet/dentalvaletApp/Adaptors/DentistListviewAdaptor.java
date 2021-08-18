package com.dentalvalet.dentalvaletApp.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.Activities.Appointments;
import com.dentalvalet.dentalvaletApp.Activities.PatientDashboard;
import com.dentalvalet.dentalvaletApp.Activities.RequestAppointment;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistEducationModel;
import com.dentalvalet.dentalvaletApp.Model.DentistExperienceModel;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Model.DentistStaffModel;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;
import com.dentalvalet.dentalvaletApp.Model.ListDataItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Awais Mahmood on 18-Nov-15.
 */
public class DentistListviewAdaptor extends RecyclerView.Adapter<DentistListviewAdaptor.MyViewHolder>
{
    private final LayoutInflater inflator;
    private clickListener clickListener;
    Context context;
    List<ListDataItem>  data = Collections.emptyList();
    private ImageLoader imageLoader;
    ArrayList<DentistInfo> dentistInfos = new ArrayList<>();
    RequestQueue queue;

    public DentistListviewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setDentistInfos(ArrayList<DentistInfo> dentistInfos) {
        this.dentistInfos = dentistInfos;

        notifyItemRangeChanged(0,dentistInfos.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_dentist_list_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }
    public void delete (int position)
    {
            data.remove(position);
            notifyItemChanged(position);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       // ListDataItem current =data.get(position);

        holder.first_slot.setVisibility(View.GONE);
        holder.second_slot.setVisibility(View.GONE);
        holder.third_slot.setVisibility(View.GONE);

        DentistInfo currentInfo = dentistInfos.get(position);
        holder.currentDentist=currentInfo;
        holder.name.setText(currentInfo.getName());
        holder.phone.setText(currentInfo.getMobile());
        imageLoader = VolleySingleton.getsInstance().getImageLoader();
        ArrayList<AppointentModel> appointmentInfos = currentInfo.getAvailableAppointments();
        if(appointmentInfos!=null)
        {
            for (int j = 0; j < appointmentInfos.size(); j++) {
                if(j==0)
                {

                    holder.third_slot_time.setText(appointmentInfos.get(j).getTime());
                    holder.third_slot_date.setText(appointmentInfos.get(j).getDate());
                    holder.selectedAppointment3=appointmentInfos.get(j);
                    holder.third_slot.setVisibility(View.VISIBLE);


                }
                else if(j==1)
                {
                    holder.second_slot_time.setText(appointmentInfos.get(j).getTime());
                    holder.second_slot_date.setText(appointmentInfos.get(j).getDate());
                    holder.second_slot.setVisibility(View.VISIBLE);
                    holder.selectedAppointment2=appointmentInfos.get(j);
                }
                else if(j==2)
                {
                    holder.first_slot_time.setText(appointmentInfos.get(j).getTime());
                    holder.first_slot_date.setText(appointmentInfos.get(j).getDate());
                    holder.first_slot.setVisibility(View.VISIBLE);
                    holder.selectedAppointment1=appointmentInfos.get(j);

                }
            }
        }




            String imageUrl = Keys.URL_DENTIST_PROFILE +currentInfo.getImage();
         if(imageUrl!=null)
        {
            Log.v("MOULIA", imageUrl);
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if(response.getBitmap() !=null)
                    {
                        Bitmap bm  = MyApplication.getsInstance().getCircleBitmap(response.getBitmap());
                        Drawable d = ResourcesCompat.getDrawable(inflator.getContext().getResources(), R.drawable.circle_for_pic, null);
                        int h = d.getIntrinsicHeight();
                        int w = d.getIntrinsicWidth();
                        bm =  Bitmap.createScaledBitmap(bm, h, w, true);
                        holder.image.setImageBitmap(bm);
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


    @Override
    public int getItemCount() {


     //   return data.size();
        return dentistInfos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name;
        DentistInfo currentDentist;
        AppointentModel selectedAppointment1;
        AppointentModel selectedAppointment2;
        AppointentModel selectedAppointment3;

        TextView phone;
        Button selectBtn;
        TextView appointment;
        TextView dentist_seeAll;
        TextView first_slot_date;
        TextView first_slot_time;
        TextView second_slot_date;
        TextView second_slot_time;
        TextView third_slot_date;
        TextView third_slot_time;
        View first_slot;
        View second_slot;
        View third_slot;



        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.dentist_image);
            name = (TextView) itemView.findViewById(R.id.dentist_name);
            phone = (TextView) itemView.findViewById(R.id.dentist_phone);
            selectBtn = (Button) itemView.findViewById(R.id.dentist_select);
            dentist_seeAll = (TextView) itemView.findViewById(R.id.dentist_seeAll);
            first_slot_date = (TextView) itemView.findViewById(R.id.dentist_first_slot_date);
            first_slot_time = (TextView) itemView.findViewById(R.id.dentist_first_slot_time);
            second_slot_date = (TextView) itemView.findViewById(R.id.dentist_second_slot_date);
            second_slot_time = (TextView) itemView.findViewById(R.id.dentist_second_slot_time);
            third_slot_date = (TextView) itemView.findViewById(R.id.dentist_third_slot_date);
            third_slot_time = (TextView) itemView.findViewById(R.id.dentist_third_slot_time);
            first_slot = (View) itemView.findViewById(R.id.dentist_first_slot);
            second_slot = (View) itemView.findViewById(R.id.dentist_second_slot);
            third_slot = (View) itemView.findViewById(R.id.dentist_third_slot);

            selectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hiring a dentist
                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
                    //Download the services and save them
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendServiceJsonRequest(String.valueOf(currentDentist.getId()));

                    HireDentistJsonRequest(String.valueOf(currentDentist.getId()), MyApplication.getsInstance().getCurrentUser().getId());


                }
            });
            dentist_seeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hiring a dentist when appointment is clicked
                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendAppointmentJsonRequest(String.valueOf(currentDentist.getId()));
                    sendServiceJsonRequest(String.valueOf(currentDentist.getId()));
                    sendEducationJsonRequest(String.valueOf(currentDentist.getId()));

                }
            });
            first_slot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
                    MyApplication.getsInstance().getCurrentUser().setSelectedAppointment(selectedAppointment1);
                    // fill the services array to show them on request appointment screen.


                    String[] serviceArray = new String[currentDentist.getAvailableServices().size() + 1];//to set the default value at 0 index
                    serviceArray[0]="Services";
                    for (int i = 0; i < currentDentist.getAvailableServices().size(); i++) {
                        ServicesModel currentService = currentDentist.getAvailableServices().get(i);
                        serviceArray[i + 1] = currentService.getTitle();
                    }

                    if (serviceArray != null) { // to show in drop down of appointment request screen

                        MyApplication.getsInstance().setAllServicesArray(serviceArray);
                    }

                    Intent intent = new Intent(context, RequestAppointment.class);
                    context.startActivity(intent);

                    // downloading jsons if the user go to the patient dashboard after appointment request click

                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendServiceJsonRequest(String.valueOf(currentDentist.getId()));
                    sendEducationJsonRequest(String.valueOf(currentDentist.getId()));
                }
            });
            second_slot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
                    MyApplication.getsInstance().getCurrentUser().setSelectedAppointment(selectedAppointment2);
                    // fill the services array to show them on request appointment screen.


                    String[] serviceArray = new String[currentDentist.getAvailableServices().size() + 1];//to set the default value at 0 index
                    serviceArray[0]="Services";
                    for (int i = 0; i < currentDentist.getAvailableServices().size(); i++) {
                        ServicesModel currentService = currentDentist.getAvailableServices().get(i);
                        serviceArray[i + 1] = currentService.getTitle();
                    }

                    if (serviceArray != null) { // to show in drop down of appointment request screen

                        MyApplication.getsInstance().setAllServicesArray(serviceArray);
                    }

                    Intent intent = new Intent(context, RequestAppointment.class);
                    context.startActivity(intent);

                    // downloading jsons if the user go to the patient dashboard after appointment request click

                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendServiceJsonRequest(String.valueOf(currentDentist.getId()));
                    sendEducationJsonRequest(String.valueOf(currentDentist.getId()));

                }
            });
            third_slot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
                    MyApplication.getsInstance().getCurrentUser().setSelectedAppointment(selectedAppointment3);
                    // fill the services array to show them on request appointment screen.


                    String[] serviceArray = new String[currentDentist.getAvailableServices().size() + 1];//to set the default value at 0 index
                    serviceArray[0]="Services";
                    for (int i = 0; i < currentDentist.getAvailableServices().size(); i++) {
                         ServicesModel currentService = currentDentist.getAvailableServices().get(i);
                        serviceArray[i + 1] = currentService.getTitle();
                    }

                    if (serviceArray != null) { // to show in drop down of appointment request screen

                        MyApplication.getsInstance().setAllServicesArray(serviceArray);
                    }



                    Intent intent = new Intent(context, RequestAppointment.class);
                    context.startActivity(intent);

                    // downloading jsons if the user go to the patient dashboard after appointment request click
                    queue = VolleySingleton.getsInstance().getRequestQueue();
                    sendServiceJsonRequest(String.valueOf(currentDentist.getId()));
                    sendEducationJsonRequest(String.valueOf(currentDentist.getId()));



                }
            });
        }

        @Override
        public void onClick(View v) {


            if (clickListener != null) {
                clickListener.itemCicked(v, getAdapterPosition());

            }
        }



        public void sendAppointmentJsonRequest(final String dentistId) {
            String url = getDentistRequestUrl(dentistId, Keys.Status.DENTIST_AVAL_APPOINTMENTS);
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
                                Log.v("MOULIA", "No Appointments");
                                //Toast.makeText(MyApplication.getAppContext(), "Search Failes", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(MyApplication.getAppContext(), "Search Successful", Toast.LENGTH_SHORT).show();
                                ArrayList<AppointentModel> appointmentInfos = new ArrayList<>();
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject currentAppointent = jsonArray.getJSONObject(i);
                                        String date;
                                        String time = currentAppointent.getString("startDateTime");
                                        String[] split = time.split("T");
                                        time = split[1];
                                        date = split[0];


                                        AppointentModel info = new AppointentModel(
                                                currentAppointent.getString("title"),
                                                currentAppointent.getString("dentistId"),
                                                currentAppointent.getString("appointmentType"),
                                                currentAppointent.getString("Id"),
                                                currentAppointent.getString("startDateTime"),
                                                currentAppointent.getString("endDateTime"), time, date);

                                        appointmentInfos.add(info);

                                        Log.v("MOULIA", info.toString());
                                    }

                                    if (appointmentInfos != null) {
                                        MyApplication.getsInstance().setAllAppointments(appointmentInfos);
                                    }

                                    sendServiceJsonRequest(dentistId);

                                    Intent intent = new Intent(context, Appointments.class);
                                    context.startActivity(intent);


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

        public String getDentistRequestUrl(String dentistId, int status) {
            // http://dentalvalet.com/api/app?dentistId=3&status=16 appointments
            return Keys.URL_DENTAL_VALET_APP +
                    Keys.URL_CHAR_QUESTION +
                    Keys.Attributes.KEY_DENTIST_ID +
                    dentistId +
                    Keys.URL_CHAR_AMPERSAND +
                    Keys.URL_PARAM_STATUS +
                    status;
        }

        //getting services

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

                                        MyApplication.getsInstance().setAllServicesArray(serviceArray);
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


        public void HireDentistJsonRequest(final String dentistId,String patientId) {
            String url = getHoreDentistRequestUrl(dentistId, patientId, Keys.Status.HIRE_DENTIST);
            Log.v("MOULIA","Hire dentist "+ url);
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

                            if (response.equals("Hired")) {
                                Intent intent = new Intent(context, PatientDashboard.class);
                                context.startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MyApplication.getAppContext(), "Dentist Cannot Be Hired.", Toast.LENGTH_SHORT).show();
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

        public String getHoreDentistRequestUrl(String dentistId,String patientId, int status) {
            // http://staging.dentalvalet.com/api/app?userId=8&dentistId=1&status=5 hire dentist
            return Keys.URL_DENTAL_VALET_APP +
                    Keys.URL_CHAR_QUESTION +
                    Keys.Attributes.KEY_USERID +
                    patientId +
                    Keys.URL_CHAR_AMPERSAND +
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

                                        Log.v("MOULIA", "Education: =" + info.getTitle() + info.getUniversity());
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





    }
    public interface clickListener {
        public void itemCicked(View v, int position);
    }

}
