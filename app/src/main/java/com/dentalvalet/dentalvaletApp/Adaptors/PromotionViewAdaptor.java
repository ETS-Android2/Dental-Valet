package com.dentalvalet.dentalvaletApp.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.Activities.Appointments;
import com.dentalvalet.dentalvaletApp.Activities.RequestAppointment;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.PromotionModel;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 03-Dec-15.
 */
public class PromotionViewAdaptor extends  RecyclerView.Adapter<PromotionViewAdaptor.MyViewHolder>  {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    private ImageLoader imageLoader;
    RequestQueue queue;
    ArrayList<PromotionModel> promotionInfo = new ArrayList<>();

    public PromotionViewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setPromotionInfos(ArrayList<PromotionModel> promotionInfo) {
        this.promotionInfo = promotionInfo;

        notifyItemRangeChanged(0,promotionInfo.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_promotion_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        PromotionModel currentInfo = promotionInfo.get(position);
        holder.promotionModel = currentInfo;
        holder.title.setText(currentInfo.getTitle());
        holder.cost.setText(currentInfo.getCost());
        holder.description.setText(currentInfo.getDescription());

        imageLoader = VolleySingleton.getsInstance().getImageLoader();
        String imageUrl = Keys.URL_PROMOTION_IMAGES+currentInfo.getId()+"/"+currentInfo.getImage();
        if(imageUrl!=null)
        {
            Log.v("MOULIA", imageUrl);
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if(response.getBitmap() !=null)
                    {
                        holder.progress.setVisibility(View.GONE);
                        Bitmap bm  = response.getBitmap();
                        holder.image.setImageBitmap(bm);
                        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
                        holder.image.setVisibility(View.VISIBLE);

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
        return promotionInfo.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView cost;
        ProgressBar progress;

        ImageView image;
        Button makeAppointment;

        PromotionModel promotionModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.promotion_title);
            description = (TextView) itemView.findViewById(R.id.promotion_description);
            cost = (TextView) itemView.findViewById(R.id.promotion_cost);
            image = (ImageView) itemView.findViewById(R.id.promotion_image);
            progress = (ProgressBar) itemView.findViewById(R.id.promotion_banner_progress);
            makeAppointment = (Button) itemView.findViewById(R.id.promotion_make_appointment);
            makeAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hiring a dentist
//                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
//                    Intent intent = new Intent(context, PatientDashboard.class);
//                    context.startActivity(intent);
                    if(MyApplication.getsInstance().getAllAppointments() == null)
                    {
                        queue = VolleySingleton.getsInstance().getRequestQueue();
                        sendAppointmentJsonRequest(promotionModel.getDentistId());

                    }
                    else
                    {
                        Intent intent = new Intent(MyApplication.getAppContext(), Appointments.class);
                        context.startActivity(intent);

                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        public void sendAppointmentJsonRequest(String dentistId) {
            String url = getAppointmentRequestUrl(dentistId, Keys.Status.DENTIST_AVAL_APPOINTMENTS);
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
                            Log.v("MOULIA", "Appointment\n"+response);

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
                                        String time  =currentAppointent.getString("startDateTime");
                                        String[] split = time.split("T");
                                        time = split[1];
                                        date = split[0];



                                        AppointentModel info = new AppointentModel(
                                                currentAppointent.getString("title"),
                                                currentAppointent.getString("dentistId"),
                                                currentAppointent.getString("appointmentType"),
                                                currentAppointent.getString("Id"),
                                                currentAppointent.getString("startDateTime"),
                                                currentAppointent.getString("endDateTime"), time,date);

                                        appointmentInfos.add(info);

                                        Log.v("MOULIA", info.toString());
                                    }

                                    if (appointmentInfos != null) {
                                        MyApplication.getsInstance().setAllAppointments(appointmentInfos);
                                    }

                                    Intent intent = new Intent(MyApplication.getAppContext(), Appointments.class);
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

        public String getAppointmentRequestUrl(String dentistId,int status) {
            // http://dentalvalet.com/api/app?dentistId=3&status=16 appointments
            return Keys.URL_DENTAL_VALET_APP +
                    Keys.URL_CHAR_QUESTION +
                    Keys.Attributes.KEY_DENTIST_ID+
                    dentistId+
                    Keys.URL_CHAR_AMPERSAND+
                    Keys.URL_PARAM_STATUS +
                    status;
        }

    }

    public interface clickListener
    {
        public void itemCicked(View v,  int position);
    }


}
