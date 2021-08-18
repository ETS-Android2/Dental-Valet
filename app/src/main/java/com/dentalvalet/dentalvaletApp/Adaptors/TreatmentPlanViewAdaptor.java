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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.TreatmentPlanModel;
import com.dentalvalet.dentalvaletApp.Model.WishlistModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 02-Dec-15.
 */
public class TreatmentPlanViewAdaptor  extends  RecyclerView.Adapter<TreatmentPlanViewAdaptor.MyViewHolder> {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    private ImageLoader imageLoader;
    RequestQueue queue;
    ArrayList<TreatmentPlanModel> treatmentPlanInfo = new ArrayList<>();

    public TreatmentPlanViewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setTreatmentPlanInfo(ArrayList<TreatmentPlanModel> treatmentPlanInfo) {
        this.treatmentPlanInfo = treatmentPlanInfo;

        notifyItemRangeChanged(0,treatmentPlanInfo.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_treatment_plan_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        TreatmentPlanModel currentInfo = treatmentPlanInfo.get(position);
        holder.treatmentPlanModel = currentInfo;
        holder.serviceTitle.setText(currentInfo.getServiceTitle());



    }


    @Override
    public int getItemCount() {


        //   return data.size();
        return treatmentPlanInfo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView serviceTitle;

        TreatmentPlanModel treatmentPlanModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            serviceTitle = (TextView) itemView.findViewById(R.id.treatment_plan_service_name);

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
