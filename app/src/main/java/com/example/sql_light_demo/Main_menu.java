package com.example.sql_light_demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main_menu extends AppCompatActivity implements View.OnClickListener{

    private Button bot_addin,bot_daka,bot_ring,bot_map;
    private FirebaseAuth mAuth;
    // [END declare_auth]
    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth.AuthStateListener authListener = null;
    TextView mainLabel = null;
    private Button signOut;
    private sql_activities helper;
    private SQLiteDatabase sql_db;
    private List<Activity> act_list,act_outs;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scroll_button);

        mAuth = FirebaseAuth.getInstance();
        mainLabel = findViewById(R.id.mainLabel);
        listView = findViewById(R.id.activity_list_4);

        bot_addin=findViewById(R.id.bot_add);
        bot_daka=findViewById(R.id.bot_daka);
        bot_ring=findViewById(R.id.bot_ring);
        bot_map=findViewById(R.id.bot_map);

        bot_addin.setOnClickListener(Main_menu.this);
        bot_daka.setOnClickListener(Main_menu.this);
        bot_ring.setOnClickListener(Main_menu.this);
        bot_map.setOnClickListener(Main_menu.this);
        listView.setAdapter(null);

        run();
        //AuthStateListener 當身份驗證狀態發生變化時調用。
        //OnAuthStateChanged 在身份驗證狀態更改時在 UI 線程中調用：
        //在監聽器註冊之後
        //當用戶登錄時
        //當前用戶退出時
        //當前用戶更改時
       authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed – user is null
                    // launch login activity
                    startActivity(new Intent(Main_menu.this, LoginActivity.class));
                    finish();
                }
            }
        };
       //帳號登出
        signOut = (Button) findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }
    //讀取各個button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bot_add:
                Intent intent_1 = new Intent();
                intent_1.setClass(this,Activity_menu.class);
                startActivity( intent_1);
                finish();
                break;

            case R.id.bot_daka:
                Intent intent_2 = new Intent();
                intent_2.setClass(this,DakaActivity.class);
                startActivity(intent_2);
                finish();
                break;

            case R.id.bot_ring:
                Intent intent_3 = new Intent();
                intent_3.setClass(this,alarm_setting.class);
                startActivity(intent_3);
                finish();
                break;

            case R.id.bot_map:
                Intent intent_4 = new Intent();
                intent_4.setClass(Main_menu.this,MainActivity_hy.class);
                startActivity(intent_4);
                finish();
                break;
        }
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload(currentUser);
            mAuth.addAuthStateListener(authListener);
        } else {
            Toast.makeText(Main_menu.this, "User not login",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Main_menu.this, SignInActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }




    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    private void reload(FirebaseUser currentUser) {
        mainLabel.setText(currentUser.getEmail());
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
            //>>>>>>>>>?
                handler.sendMessage(handler.obtainMessage(1000,str));
                Thread.sleep(1000);//实时显示当前时间


                //将数据库中的数据放入listView中
                sql_activities helper=new sql_activities(Main_menu.this);
                sql_db = helper.getReadableDatabase();
                Cursor cursor = sql_db.query("activitys_arrangement",null,null,null,null,null,null);
                if(cursor.getCount() == 0) {
                    Toast.makeText(this,"目前沒有活動",Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    act_list = new ArrayList<Activity>();
                    cursor.moveToFirst();

                    do {
                        //讀取資料庫裏面的值
                        String num = cursor.getString(0);
                        String names = cursor.getString(1);
                        String time = cursor.getString(2);
                        String adress = cursor.getString(3);
                        String priority = cursor.getString(4);


                        try{
                            //规定格式 (格式根据自己数据库取得的数据进行规范)
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //获取当前时间
                            Date date = new Date(System.currentTimeMillis());
                            String date3=sdf.format(date);
                            if((time.compareTo(String.valueOf(date3)))>0){
                                Activity activityOBJ = new Activity(num,names,time,adress,priority/*,null*/);
                                act_list.add(activityOBJ);
                            }

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }while (cursor.moveToNext());
                        //排序時間，由小到大
                    act_outs=new ArrayList<Activity>();
                    int counts_act=act_list.size();
                    for (int i=0;i<counts_act;i++)
                    {
                        String min = act_list.get(i).getTime();
                        for (int j=i+1;j<counts_act;j++){
                            if (min.compareTo(act_list.get(j).getTime())>0){
                                min = act_list.get(j).getTime();
                                Activity t= act_list.get(i);
                                act_list.set(i, act_list.get(j));
                                act_list.set(j, t);
                            }
                        }
                        act_outs.add(act_list.get(i));
                    }


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
            View view = View.inflate(Main_menu.this,R.layout.ningbao,null);

            Activity activity = act_outs.get(position);

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
