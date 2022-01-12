package com.example.sql_light_demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class menuFragment extends Fragment {
    private View view;
    private Button btadd;
    private Button btedit;
    private Button btback;
    private RecyclerView recyclerView;
    private sql_activities helper;
    private SQLiteDatabase sql_db;
    RecyclerViewAdapter mAdapter;
    private List<Activity> activity,act_outs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        btadd = view.findViewById(R.id.button_add);
        btedit = view.findViewById(R.id.button_edit);
        btback = view.findViewById(R.id.button_back);
        recyclerView = view.findViewById(R.id.recyclerView_ActivityList);
        helper=new sql_activities(getActivity());

        sql_db = helper.getReadableDatabase();
        //???????????????
        Cursor cursor = sql_db.query("activitys_arrangement",null,null,null,null,null,null);
        activity = new ArrayList<Activity>();
        cursor.moveToFirst();
        if(cursor.getCount()==0)
        {

        }
        else
        {
            do {
                String num =cursor.getString(0);
                String names = cursor.getString(1);
                String time = cursor.getString(2);
                String adress = cursor.getString(3);
                String priority = cursor.getString(4);
                String record =cursor.getString(5);

                Activity activity_sums = new Activity(num, names, time, adress, priority, record);
                activity.add(activity_sums);

            } while (cursor.moveToNext());

            act_outs=new ArrayList<Activity>();
            int counts_act=activity.size();
            for (int i=0;i<counts_act;i++)
            {
                String min = activity.get(i).getTime();
                for (int j=i+1;j<counts_act;j++){
                    if (min.compareTo(activity.get(j).getTime())>0){
                        min = activity.get(j).getTime();
                        Activity t;
                        t = activity.get(i);
                        activity.set(i, activity.get(j));
                        activity.set(j, t);
                    }
                }
                act_outs.add(activity.get(i));
            }

            cursor.close();
            mAdapter = new RecyclerViewAdapter(act_outs);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
            sql_db.close();
        }

        return view;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        btadd = view.findViewById(R.id.button_add);
        btedit = view.findViewById(R.id.button_edit);
        btback = view.findViewById(R.id.button_back);
        recyclerView = view.findViewById(R.id.recyclerView_ActivityList);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        btadd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),MainActivity.class);     //changes_in_1后面需要替代为主界面class
                startActivity(intent);
            }
        });
        btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),changes_in_1.class);     //changes_in_1后面需要替代为主界面class
                startActivity(intent);
            }
        });
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),Main_menu.class);     //changes_in_1后面需要替代为主界面class
                startActivity(intent);
            }
        });


    }
}