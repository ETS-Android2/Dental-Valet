package com.dentalvalet.dentalvaletApp.Adaptors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dentalvalet.dentalvaletApp.Activities.DentistStaff;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.DentistEducationModel;
import com.dentalvalet.dentalvaletApp.Model.DentistStaffModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 09-Dec-15.
 */
public class DentistStaffViewAdaptor extends RecyclerView.Adapter<DentistStaffViewAdaptor.MyViewHolder>  {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    ArrayList<DentistStaffModel> staffInfo = new ArrayList<>();
    private ImageLoader imageLoader;


    public DentistStaffViewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setStaffInfos(ArrayList<DentistStaffModel> staffInfo) {
        this.staffInfo = staffInfo;

        notifyItemRangeChanged(0,staffInfo.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_dentist_staff_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        DentistStaffModel currentInfo = staffInfo.get(position);
        holder.dentistStaffModel = currentInfo;
        holder.name.setText(currentInfo.getName());
        holder.position.setText(currentInfo.getPosition());

        holder.descption.setText(currentInfo.getDescription());


        imageLoader = VolleySingleton.getsInstance().getImageLoader();
        String imageUrl = Keys.URL_DENTIST_STAFF +currentInfo.getImage();
        if(imageUrl!=null)
        {
            Log.v("MOULIA", "Services" + imageUrl);
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
                        Bitmap bm = MyApplication.getsInstance().getCircleBitmap(response.getBitmap());
                        Drawable d = ResourcesCompat.getDrawable(inflator.getContext().getResources(), R.drawable.circle_for_pic, null);
                        int h = d.getIntrinsicHeight();
                        int w = d.getIntrinsicWidth();
                        bm = Bitmap.createScaledBitmap(bm, h, w, true);
                        holder.image.setImageBitmap(bm);
                    } else {
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
        return staffInfo.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView position;
        TextView descption;

        ImageView image;
        DentistStaffModel dentistStaffModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.staff_name);
            position = (TextView) itemView.findViewById(R.id.staff_position);
            descption = (TextView) itemView.findViewById(R.id.staff_description);
            image = (ImageView) itemView.findViewById(R.id.staff_image);

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
