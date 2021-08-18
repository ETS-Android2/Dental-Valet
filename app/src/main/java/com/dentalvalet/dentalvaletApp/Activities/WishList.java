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

import com.dentalvalet.dentalvaletApp.Adaptors.ServicesViewAdaptor;
import com.dentalvalet.dentalvaletApp.Adaptors.WishlistViewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 28-Nov-15.
 */
public class WishList extends FragmentActivity implements WishlistViewAdaptor.clickListener {
    private RecyclerView recyclerView;
    private WishlistViewAdaptor adaptor;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (MyApplication.getsInstance().getCurrentUser().getPatientWishlist() == null) {
            setContentView(R.layout.empty_wishlist);
        } else {
            if (MyApplication.getsInstance().getCurrentUser().getPatientWishlist().isEmpty()) {
                setContentView(R.layout.empty_wishlist);
            } else {
                setContentView(R.layout.wishlist);
                recyclerView = (RecyclerView) findViewById(R.id.wishlist_list);
                adaptor = new WishlistViewAdaptor(WishList.this);
                adaptor.setWishlistInfo(MyApplication.getsInstance().getCurrentUser().getPatientWishlist());
                adaptor.setClickListener(this);
                recyclerView.setAdapter(adaptor);
                recyclerView.setLayoutManager(new LinearLayoutManager(WishList.this));


                backBtn = (Button) findViewById(R.id.dentist_refer_btn);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(WishList.this, ReferDentist.class);
                        startActivity(intent);
                    }
                });

            }
        }


        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_wishlist")) {
                    finish();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_wishlist"));


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
