package com.example.final_test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity_hy extends AppCompatActivity {
    String done="0";
    CheckJF checkJF=new CheckJF();
    private SQLiteDatabase sql_db;
    private sql_activities helper;
    private List<Activity> activity;
    private List<Activity> act_list;
    private ListView listView;
    int jf=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hy);
        listView = findViewById(R.id.activity_list_5);

        run();

        helper=new sql_activities(this);
        sql_db = helper.getReadableDatabase();
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
                jf = jf + Integer.parseInt(record);
            } while (cursor.moveToNext());
            cursor.close();
            sql_db.close();
        }
        done = Integer.toString(jf);
        TextView tv_2=findViewById(R.id.tv_2);
        TextView tv_4=findViewById(R.id.tv_4);
        tv_2.setText("整整"+done+"個！");
        tv_4.setText(checkJF.CJF(jf));

        Button btn_01=(Button)findViewById(R.id.btn_01);
//注意，创建button对象时3.0之前的android studio需要强转类型
        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_hy.this,MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btn_02=(Button)findViewById(R.id.btn_04);
//注意，创建button对象时3.0之前的android studio需要强转类型
        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_hy.this,Main_menu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };

    public void run(){
        try{
            while (true){
                SimpleDateFormat formatter   =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
                Date curDate =  new Date(System.currentTimeMillis());
                //获取当前时间
                String str = formatter.format(curDate);
                handler.sendMessage(handler.obtainMessage(100,str));
                Thread.sleep(1000);//实时显示当前时间


                //将数据库中的数据放入listView中
                sql_activities helper=new sql_activities(MainActivity_hy.this);
                sql_db = helper.getReadableDatabase();
                Cursor cursor = sql_db.query("activitys_arrangement",null,null,null,null,null,null);
                if(cursor.getCount() == 0) {
                    break;
                }
                else {
                    act_list = new ArrayList<Activity>();
                    cursor.moveToFirst();



                    do {
                        String num = cursor.getString(0);
                        String names = cursor.getString(1);
                        String time = cursor.getString(2);
                        String adress = cursor.getString(3);
                        String priority = cursor.getString(4);
                        String record = cursor.getString(5);

                        try{
                            if(Integer.parseInt(record)==1){
                                Activity activityOBJ = new Activity(num,names,time,adress,priority/*,null*/);
                                act_list.add(activityOBJ);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }while (cursor.moveToNext());


                    listView.setAdapter(new ActivityAdapter());
                    cursor.close();
                    sql_db.close();
                    break;
                }
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    class ActivityAdapter extends BaseAdapter {

        public ActivityAdapter(){
            super();
        }

        @Override
        public int getCount() {//获取数据条数
            return act_list.size();
        }

        @Override
        public Object getItem(int position) {
            return act_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//将position所指的数据填充到ListView中
            View view = View.inflate(MainActivity_hy.this,R.layout.ningbao,null);

            Activity activity = act_list.get(position);

            TextView tv1 = view.findViewById(R.id.num);
            tv1.setText(activity.getNum());

            TextView tv2 = view.findViewById(R.id.name);
            tv2.setText(activity.getName());

            TextView tv3 = view.findViewById(R.id.date);
            tv3.setText(activity.getTime());

            TextView tv4 = view.findViewById(R.id.address);
            tv4.setText(activity.getAdress());

            TextView tv5 = view.findViewById(R.id.priority);
            tv5.setText(activity.getPriority());


            return view;
        }
    }
}