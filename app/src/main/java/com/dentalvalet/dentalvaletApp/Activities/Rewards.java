package com.dentalvalet.dentalvaletApp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.dentalvalet.dentalvaletApp.Adaptors.PromotionViewAdaptor;
import com.dentalvalet.dentalvaletApp.Adaptors.RewardViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class Rewards extends FragmentActivity implements  RewardViewAdaptor.clickListener {

    private RecyclerView recyclerView;
    private RewardViewAdaptor adaptor;

    Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistRewards()==null)
        {
            setContentView(R.layout.empty_rewards);
        }
        else {

            if(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistRewards().isEmpty())
            {
                setContentView(R.layout.empty_rewards);
            }
            else {
                setContentView(R.layout.rewards);

                recyclerView = (RecyclerView) findViewById(R.id.reward_list);
                adaptor = new RewardViewAdaptor(Rewards.this);
                adaptor.setRewardInfo(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistRewards());
                adaptor.setClickListener(this);
                recyclerView.setAdapter(adaptor);
                recyclerView.setLayoutManager(new LinearLayoutManager(Rewards.this));


                backBtn = (Button)findViewById(R.id.dentist_refer_btn);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Rewards.this, ReferDentist.class);
                        startActivity(intent);
                    }
                });
            }

        }


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
