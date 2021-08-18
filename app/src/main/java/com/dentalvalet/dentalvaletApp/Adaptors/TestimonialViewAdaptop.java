package com.dentalvalet.dentalvaletApp.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Model.TestimonialModel;
import com.dentalvalet.dentalvaletApp.Model.TreatmentPlanModel;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 03-Dec-15.
 */
public class TestimonialViewAdaptop extends  RecyclerView.Adapter<TestimonialViewAdaptop.MyViewHolder>  {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    private ImageLoader imageLoader;
    RequestQueue queue;
    ArrayList<TestimonialModel> testimonialInfo = new ArrayList<>();

    public TestimonialViewAdaptop(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setTestionialInfo(ArrayList<TestimonialModel> testimonialInfo) {
        this.testimonialInfo = testimonialInfo;

        notifyItemRangeChanged(0,testimonialInfo.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_testimonial_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        TestimonialModel currentInfo = testimonialInfo.get(position);
        holder.testimonialModel = currentInfo;
        holder.description.setText(currentInfo.getDescription());
        holder.dentistName.setText(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getName());



    }


    @Override
    public int getItemCount() {


        //   return data.size();
        return testimonialInfo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView description;
        TextView dentistName;

        TestimonialModel testimonialModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            description = (TextView) itemView.findViewById(R.id.testimonial_row_description);
            dentistName = (TextView) itemView.findViewById(R.id.testimonial_dentist_name);

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
