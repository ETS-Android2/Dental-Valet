package com.dentalvalet.dentalvaletApp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dentalvalet.dentalvaletApp.Adaptors.PromotionViewAdaptor;
import com.dentalvalet.dentalvaletApp.Adaptors.ServicesViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class Promotions extends FragmentActivity implements PromotionViewAdaptor.clickListener  {

    private RecyclerView recyclerView;
    private PromotionViewAdaptor adaptor;
    Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistPromotions()==null)
        {
            setContentView(R.layout.empty_promotions);
        }
        else
        {
            if(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistPromotions().isEmpty())
            {
                setContentView(R.layout.empty_promotions);
            }
            else
            {
                setContentView(R.layout.promotions);

                recyclerView = (RecyclerView) findViewById(R.id.promotion_list);
                adaptor = new PromotionViewAdaptor(Promotions.this);
                adaptor.setPromotionInfos(MyApplication.getsInstance().getCurrentUser().getSelectedDentist().getDentistPromotions());
                adaptor.setClickListener(this);
                recyclerView.setAdapter(adaptor);
                recyclerView.setLayoutManager(new LinearLayoutManager(Promotions.this));


                BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

                    @Override
                    public void onReceive(Context arg0, Intent intent) {
                        String action = intent.getAction();
                        if (action.equals("finish_promotion")) {
                            finish();
                            // DO WHATEVER YOU WANT.
                        }
                    }
                };
                registerReceiver(broadcast_reciever, new IntentFilter("finish_promotion"));

                backBtn = (Button)findViewById(R.id.dentist_refer_btn);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Promotions.this, ReferDentist.class);
                        startActivity(intent);
                    }
                });

            }


        }

    }

    @Override
    public void itemCicked(View v, int position) {

    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }
}
