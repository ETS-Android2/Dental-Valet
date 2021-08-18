package com.dentalvalet.dentalvaletApp.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dentalvalet.dentalvaletApp.Activities.RequestAppointment;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistExperienceModel;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 04-Dec-15.
 */
public class ExperienceViewAdaptor extends RecyclerView.Adapter<ExperienceViewAdaptor.MyViewHolder> {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    ArrayList<DentistExperienceModel> experienceInfo = new ArrayList<>();
    public ExperienceViewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setExperienceInfos(ArrayList<DentistExperienceModel> experienceInfo) {
        this.experienceInfo = experienceInfo;

        notifyItemRangeChanged(0,experienceInfo.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_row_experiene,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        DentistExperienceModel currentInfo = experienceInfo.get(position);
        holder.dentistExperienceModel = currentInfo;
        holder.title.setText(currentInfo.getTitle());
        holder.years.setText(currentInfo.getDuration());


    }


    @Override
    public int getItemCount() {


        //   return data.size();
        return experienceInfo.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView years;
        DentistExperienceModel dentistExperienceModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.experience_title);
            years = (TextView) itemView.findViewById(R.id.experience_years);

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

