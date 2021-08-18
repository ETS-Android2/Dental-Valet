package com.dentalvalet.dentalvaletApp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dentalvalet.dentalvaletApp.Adaptors.DentistStaffViewAdaptor;
import com.dentalvalet.dentalvaletApp.Adaptors.EducationViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 06-Dec-15.
 */
public class DentistStaff extends FragmentActivity  implements DentistStaffViewAdaptor.clickListener{
    private RecyclerView recyclerView;
    private DentistStaffViewAdaptor adaptor;
    Button dashboard_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dentist_staff);

        recyclerView = (RecyclerView) findViewById(R.id.staff_list);
        adaptor = new DentistStaffViewAdaptor(DentistStaff.this);
        adaptor.setStaffInfos(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistStaff());
        adaptor.setClickListener(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(DentistStaff.this));
        dashboard_btn= (Button)findViewById(R.id.dashboard_btn);

        dashboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });



    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    @Override
    public void itemCicked(View v, int position) {

    }
}
