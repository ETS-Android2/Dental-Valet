package com.dentalvalet.dentalvaletApp.Adaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.WishlistModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_CHAR_AMPERSAND;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_CHAR_QUESTION;
import static com.dentalvalet.dentalvaletApp.Extras.Keys.URL_PARAM_STATUS;

/**
 * Created by Awais Mahmood on 01-Dec-15.
 */
public class WishlistViewAdaptor extends  RecyclerView.Adapter<WishlistViewAdaptor.MyViewHolder> {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    private ImageLoader imageLoader;
    RequestQueue queue;
    ArrayList<WishlistModel> wishlistInfo = new ArrayList<>();

    public WishlistViewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setWishlistInfo(ArrayList<WishlistModel> wishlistInfo) {
        this.wishlistInfo = wishlistInfo;

        notifyItemRangeChanged(0,wishlistInfo.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_wishlist_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        WishlistModel currentInfo = wishlistInfo.get(position);
        holder.wishlistModel = currentInfo;
        holder.title.setText(currentInfo.getTitle());
        holder.dentistTime.setText(currentInfo.getDetistTime());
        holder.hygineTime.setText(currentInfo.getHygienistTime());




        imageLoader = VolleySingleton.getsInstance().getImageLoader();
        String imageUrl = Keys.URL_DENTIST_SERVICE +currentInfo.getImage();
        if(imageUrl!=null)
        {
            Log.v("MOULIA", "wishlist"+imageUrl);
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
        return wishlistInfo.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView dentistTime;
        TextView hygineTime;

        ImageView image;
        Button remove;

        WishlistModel wishlistModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.wishtlist_title);
            dentistTime = (TextView) itemView.findViewById(R.id.wishtlist_dentist_time);
            hygineTime = (TextView) itemView.findViewById(R.id.wishtlist_hygine_time);

            image = (ImageView) itemView.findViewById(R.id.wishtlist_image);
            remove = (Button) itemView.findViewById(R.id.wishtlist_remove);


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hiring a dentist
//                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
//                    Intent intent = new Intent(context, PatientDashboard.class);
//                    context.startActivity(intent);


                    new AlertDialog.Builder(context)
                            .setTitle("Caution !")
                            .setMessage("Do you really want to delete this service from your wishlist?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    queue = VolleySingleton.getsInstance().getRequestQueue();
                                    sendJsonRequest(MyApplication.getsInstance().getCurrentUser().getId(),wishlistModel.getServiceId(),Keys.Status.REMOVE_SERVICE);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

        }

        public void sendJsonRequest(String patientId, String serviceId,int status) {
            String url = getRemoveWishlistUrl(patientId, serviceId, status);
            Log.v("MOULIA", "WISHLIST REMOVE URL "+url);

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
                    Toast.makeText(MyApplication.getAppContext(), "Check You Internet Connection", Toast.LENGTH_SHORT).show();

                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);


        }

        public String getRemoveWishlistUrl(String patientId, String serviceId,int status) {
            //Registration
            //http://dentalvalet.com/api/App?status=39&patientId=1012&serviceId=025 remove wishlist
            return "http://dentalvalet.com/api/App" +
                    URL_CHAR_QUESTION +
                    URL_PARAM_STATUS +
                    status+
                    URL_CHAR_AMPERSAND +
                    Keys.Attributes.KEY_PATIENT_ID +
                    patientId +
                    URL_CHAR_AMPERSAND +
                    Keys.Attributes.KEY_SERVICE_ID +
                    serviceId;

        }

        public void parseJSONResponse(String response) {
            // Display the first 500 characters of the response string.
            response = response.replace("\\", "");
            response = response.replace("/", "");
            response = response.substring(1, response.length() - 1);
            //  Log.v("MOULIA", response);
            //Integer val = Integer.valueOf(response);


            Toast.makeText(MyApplication.getAppContext(), response.toString(), Toast.LENGTH_SHORT).show();
            MyApplication.getsInstance().getCurrentUser().getPatientWishlist().remove(this.wishlistModel);
            notifyDataSetChanged();

        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface clickListener
    {
        public void itemCicked(View v,  int position);
    }

}
