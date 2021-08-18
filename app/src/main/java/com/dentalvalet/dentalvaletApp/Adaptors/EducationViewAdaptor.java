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
import com.dentalvalet.dentalvaletApp.Model.DentistEducationModel;
import com.dentalvalet.www.dentalvaletApp.R;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 04-Dec-15.
 */
public class EducationViewAdaptor extends  RecyclerView.Adapter<EducationViewAdaptor.MyViewHolder> {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    ArrayList<DentistEducationModel> EducationInfo = new ArrayList<>();

    public EducationViewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setEducationInfos(ArrayList<DentistEducationModel> EducationInfo) {
        this.EducationInfo = EducationInfo;

        notifyItemRangeChanged(0,EducationInfo.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_education_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        DentistEducationModel currentInfo = EducationInfo.get(position);
        holder.dentistEducationModel = currentInfo;
        holder.title.setText(currentInfo.getTitle());
        holder.university.setText(currentInfo.getUniversity());

        holder.startDate.setText(currentInfo.getStartDate());

        holder.endDate.setText(currentInfo.getEndDate());


    }


    @Override
    public int getItemCount() {


        //   return data.size();
        return EducationInfo.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView university;
        TextView startDate;
        TextView endDate;

        DentistEducationModel dentistEducationModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.education_degree_title);
            university = (TextView) itemView.findViewById(R.id.education_university);
            startDate = (TextView) itemView.findViewById(R.id.education_startdate);
            endDate = (TextView) itemView.findViewById(R.id.education_enddate);

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
