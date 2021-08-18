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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dentalvalet.dentalvaletApp.Activities.PatientDashboard;
import com.dentalvalet.dentalvaletApp.Activities.RequestAppointment;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.DentistInfo;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 01-Dec-15.
 */
public class AppointmentViewAdaptor extends  RecyclerView.Adapter<AppointmentViewAdaptor.MyViewHolder> {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    ArrayList<AppointentModel> appointmentInfo = new ArrayList<>();


    public AppointmentViewAdaptor(Context context) {
        this.context=context;
        inflator = LayoutInflater.from(context);
    }

    public void setAppointmentInfos(ArrayList<AppointentModel> appointmentInfos) {
        this.appointmentInfo = appointmentInfos;

        notifyItemRangeChanged(0,appointmentInfos.size());
    }


    public void setClickListener(clickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_appointment_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        AppointentModel currentInfo = appointmentInfo.get(position);
        holder.appointentModel = currentInfo;
        holder.date.setText(currentInfo.getDate());
        holder.time.setText(currentInfo.getTime());


    }


    @Override
    public int getItemCount() {


        //   return data.size();
        return appointmentInfo.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView time;
        TextView date;
        Button selectBtn;
        AppointentModel appointentModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            time = (TextView) itemView.findViewById(R.id.time);
            date = (TextView) itemView.findViewById(R.id.date);
            selectBtn = (Button) itemView.findViewById(R.id.appointment_select);
            selectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hiring a dentist
//                    MyApplication.getsInstance().getCurrentUser().setSelectedDentist(currentDentist);
//                    Intent intent = new Intent(context, PatientDashboard.class);
//                    context.startActivity(intent);
                    MyApplication.getsInstance().getCurrentUser().setSelectedAppointment(appointentModel);
                    Intent intent = new Intent(context, RequestAppointment.class);
                    context.startActivity(intent);
                }
            });
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
