package com.dentalvalet.dentalvaletApp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dentalvalet.dentalvaletApp.Adaptors.DentistListviewAdaptor;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Model.ListDataItem;

import java.util.ArrayList;
import java.util.List;

import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 18-Nov-15.
 */
public class FindDentistFragment extends Fragment implements DentistListviewAdaptor.clickListener {
    private RecyclerView recyclerView;
    private long dentistId;
    private DentistListviewAdaptor adaptor;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout =inflater.inflate(R.layout.list_view_dentist,container,false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.dentistList);
        adaptor = new DentistListviewAdaptor(getActivity());
        adaptor.setDentistInfos(MyApplication.getsInstance().getAllDentists());
        adaptor.setClickListener(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyApplication.getsInstance().setAdaptor(adaptor);
        return layout;


    }


    public static  List<ListDataItem> getData()
    {
        List<ListDataItem> array = new ArrayList<>();
        int[] icon = {R.drawable.circle_for_pic, R.drawable.circle_for_pic,R.drawable.circle_for_pic, R.drawable.circle_for_pic};
        String[] text  = {"text1","text2","text3","text4"};

        for(int i=0;i<=100;i++)
        {
            ListDataItem current = new ListDataItem();
            current.setIconId(icon[i%icon.length]);
            current.setName(text[i%icon.length]);
            array.add(current);
        }

        return array;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public FindDentistFragment() {
        super();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }

    @Override
    public void itemCicked(View v, int position) {
       // Toast.makeText(getActivity(), "I have clicked"+ position + " item", Toast.LENGTH_SHORT).show();

    }



}
