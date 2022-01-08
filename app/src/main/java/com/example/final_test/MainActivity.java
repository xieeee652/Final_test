package com.example.final_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nubs,names,addresses;
    private EditText time_y,time_month,time_d,time_h,time_min,time_s;
    private EditText deleted_nub;
    private ListView list_view_1;
    private Button bot_1,bot_2,bot_4,bot_6,bot_back,bot_next;
    private sql_activities helper;
    private SQLiteDatabase sql_db;
    private List<Activity> act_list,act_outs;
    private String lite_1;
    private String CHANNEL_ID = "ChannelID";

    private RadioGroup select_ac_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lite_1="activitys_arrangement";

        nubs=findViewById(R.id.update_nubs);
        names=findViewById(R.id.update_name);
        time_y=findViewById(R.id.update_date_time_years);
        time_month=findViewById(R.id.update_date_time_month);
        time_d=findViewById(R.id.update_date_time_day);
        time_h=findViewById(R.id.update_date_time_hour);
        time_min=findViewById(R.id.update_date_time_min);
        time_s=findViewById(R.id.update_date_time_se);
        addresses=findViewById(R.id.update_address);
        deleted_nub=findViewById(R.id.delete_nub);
        list_view_1=findViewById(R.id.list_views_1);

        bot_1=findViewById(R.id.bot_1);
        bot_2=findViewById(R.id.bot_2);
        bot_4=findViewById(R.id.bot_4);
        bot_6=findViewById(R.id.bot_6);
        bot_back=findViewById(R.id.bot_00);
        bot_next=findViewById(R.id.bot_next);

        bot_1.setOnClickListener(this);
        bot_2.setOnClickListener(this);
        bot_4.setOnClickListener(this);
        bot_6.setOnClickListener(this);
        bot_back.setOnClickListener(this);
        bot_next.setOnClickListener(this);

        select_ac_priority=findViewById(R.id.select_priority);
        select_ac_priority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {

                RadioButton radioButton = radioGroup.findViewById(i);
                ac_priority= (String) radioButton.getText();
            }
        });
        //創建並打開數據庫
        helper=new sql_activities(this);
        list_view_1.setAdapter(null);
    }

    public String ac_priority="";

    @Override
    public void  onClick(View v)
    {
        String ac_na=names.getText().toString().trim();

        String ac_times="  ";
        String t_y=time_y.getText().toString().trim();
        String t_mo=time_month.getText().toString().trim();
        String t_d=time_d.getText().toString().trim();
        String t_h=time_h.getText().toString().trim();
        String t_mi=time_min.getText().toString().trim();
        String t_se=time_s.getText().toString().trim();

        String ac_ad=addresses.getText().toString().trim();
        String ac_nubs=nubs.getText().toString().trim();


        switch (v.getId())
        {
            case R.id.bot_1://讀取輸入的所有信息，並將信息讀入資料庫中


                if (ac_na == " " || t_y == " " || t_mo == " " || t_d == " " || t_h == " " || t_mi == " " || t_se == " " || ac_ad == " " || ac_nubs == " ")
                {
                    Toast.makeText(this,"信息不完整，請輸入完整信息!",Toast.LENGTH_LONG).show();
                    break;
                }
                //進行輸入數據的判定
                int t_ys=Integer.parseInt(t_y);
                if ( t_ys>2025 || t_ys < 2021)
                {
                    Toast.makeText(this,"年份輸入有誤，請重新輸入!",Toast.LENGTH_LONG).show();
                    break;
                }

                int t_mos=Integer.parseInt(t_mo);
                if ( t_mos >12 || t_mos < 0)
                {
                    Toast.makeText(this,"月份輸入有誤，請重新輸入!",Toast.LENGTH_LONG).show();
                    break;
                }
                else
                if (t_mos<10 && t_mo.length()<2 ) t_mo='0'+t_mo;

                int t_ds=Integer.parseInt(t_d);
                if ( t_ds > 31 || t_ds < 0)
                {
                    Toast.makeText(this,"日期輸入有誤，請重新輸入!",Toast.LENGTH_LONG).show();
                    break;
                }
                else
                if (t_ds<10 && t_d.length()<2 ) t_d='0'+t_d;

                int t_hs=Integer.parseInt(t_h);
                if ( t_hs >24 || t_hs < 0)
                {
                    Toast.makeText(this,"小時輸入有誤，請重新輸入!",Toast.LENGTH_LONG).show();
                    break;
                }
                else
                if (t_hs<10 && t_h.length()<2 ) t_h='0'+t_h;

                int t_mis=Integer.parseInt(t_mi);
                if ( t_mis >60 || t_mis < 0)
                {
                    Toast.makeText(this,"分鐘輸入有誤，請重新輸入!",Toast.LENGTH_LONG).show();
                    break;
                }
                else
                if (t_mis<10 && t_mi.length()<2 ) t_mi='0'+t_mi;

                int t_ses=Integer.parseInt(t_se);
                if ( t_ses >60 || t_ses < 0)
                {
                    Toast.makeText(this,"秒數輸入有誤，請重新輸入!",Toast.LENGTH_LONG).show();
                    break;
                }
                else
                if (t_ses<10 && t_se.length()<2 ) t_se='0'+t_se;

                ac_times=t_y+"-"+t_mo+"-"+t_d+" "+t_h+":"+t_mi+":"+t_se;

                ContentValues values=new ContentValues();

                values.put("num",ac_nubs);
                values.put("names",ac_na);
                values.put("data_time",ac_times);
                values.put("address",ac_ad);
                values.put("priority",ac_priority);
                values.put("record","0");
                sql_db=helper.getWritableDatabase();
                Long postion=sql_db.insert(lite_1,null,values);
                sql_db.close();
                Toast.makeText(this,"已新增活動："+ac_na,Toast.LENGTH_LONG).show();
                createNotificationChannel();
                Intent intent2 = new Intent(this, Main_menu.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal_background)
                        .setContentTitle(ac_na)
                        .setContentText("已添加到活動數據庫中！")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                // notificationId is a unique int for each notification that you must define
                int notificationId = 1;
                notificationManager.notify(notificationId, builder.build());

                break;

            case R.id.bot_2://清空所有TextView中的數據
                nubs.setText(" ");
                names.setText(" ");
                time_y.setText(" ");
                time_month.setText(" ");
                time_d.setText(" ");
                time_h.setText(" ");
                time_min.setText(" ");
                time_s.setText(" ");
                addresses.setText(" ");
                break;

            case R.id.bot_4://根據序號刪除數據庫中的紀錄
                String delete_nubs=deleted_nub.getText().toString().trim();

                sql_db=helper.getWritableDatabase();
                int deleted=sql_db.delete(lite_1,"num=?",new String[]{delete_nubs});
                sql_db.close();

                deleted_nub.setText(" ");

                break;

            case R.id.bot_6:            //查看已經存入數據庫的數量(只能查某一個表格的内容，不可以跨表查詢)
                sql_db = helper.getReadableDatabase();
                Cursor cursor = sql_db.query("activitys_arrangement",null,null,null,null,null,null);
                act_list = new ArrayList<Activity>();
                cursor.moveToFirst();
                if(cursor.getCount()==0)
                {
                    Toast.makeText(this,"數據庫中還沒有任何活動信息，請先輸入活動信息",Toast.LENGTH_LONG).show();
                    break;
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
                        act_list.add(activity_sums);

                    } while (cursor.moveToNext());

                    act_outs=new ArrayList<Activity>();
                    int counts_act=act_list.size();
                    for (int i=0;i<counts_act;i++)
                    {
                        act_outs.add(act_list.get(i));
                    }

                    list_view_1.setAdapter(new ActivityAdapter());
                    cursor.close();
                    sql_db.close();
                }
                break;


            case R.id.bot_00:
                Intent intent = new Intent();
                intent.setClass(this,Activity_menu.class);     //changes_in_1後面需要替代為主畫面class
                startActivity( intent);
                finish();
                break;

            case R.id.bot_next:
                Intent intent_2 = new Intent();
                intent_2.setClass(this,changes_in_1.class);     //changes_in_1後面需要替代為主畫面class
                startActivity( intent_2);
                finish();
                break;
        }
    }

    class ActivityAdapter extends BaseAdapter {

        public ActivityAdapter(){
            super();
        }

        @Override
        public int getCount() {//获取数据条数
            return act_outs.size();
        }

        @Override
        public Object getItem(int position) {
            return act_outs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//將position所指的數據填入ListView中
            View view = View.inflate(MainActivity.this,R.layout.list_item_1,null);

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

            TextView tv6 = view.findViewById(R.id.record);
            tv6.setText(activity.getRecord());

            return view;
        }
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
