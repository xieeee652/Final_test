package com.example.final_test;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.AlarmClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class alarm_setting extends AppCompatActivity implements View.OnClickListener{
    private sql_activities helper;
    private TextView current_time;
    private SQLiteDatabase sql_db;
    private Button daka_cc,back_bot;
    private List name;
    private List<Activity> act_list;
    private ListView listView;
    private Button button1,button2,button3,button4;
    private String tt[]=new String[10];
    private int tt1=1;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        listView = findViewById(R.id.activity_list_3);
        daka_cc=findViewById(R.id.bot_3);
        back_bot=findViewById(R.id.back_menu);
        spinner=findViewById(R.id.spinner);
        listView.setAdapter(null);

        //init();
        run();


        back_bot.setOnClickListener(this);
        daka_cc.setOnClickListener(this);


    }

    private void createAlarm(String message,int day,int hour,int minutes) {

        String packageName = getApplication().getPackageName();
        System.out.println(day+hour+minutes);
        //action为AlarmClock.ACTION_SET_ALARM
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)

                .putExtra(AlarmClock.EXTRA_DAYS,day)
                //闹钟的小时
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                //响铃时提示的信息
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                //闹钟的分钟
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                //用于指定该闹铃触发时是否振动
                .putExtra(AlarmClock.EXTRA_VIBRATE, true)

                //一个 ArrayList，其中包括应重复触发该闹铃的每个周日。
                // 每一天都必须使用 Calendar 类中的某个整型值（如 MONDAY）进行声明。
                //如果为true，则调用startActivity()不会进入手机的闹钟设置界面
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startActivity(intent);
        Toast.makeText(this,"叮叮当",Toast.LENGTH_SHORT).show();

    }

    private void createAlarm1(String message, int hour, int minutes, int resId) {
        ArrayList<Integer> testDays = new ArrayList<>();
        testDays.add(Calendar.MONDAY);//周一
        testDays.add(Calendar.TUESDAY);//周二
        testDays.add(Calendar.FRIDAY);//周五

        String packageName = getApplication().getPackageName();
        Uri ringtoneUri = Uri.parse("android.resource://" + packageName + "/" + resId);
        //action为AlarmClock.ACTION_SET_ALARM
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                //闹钟的小时
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                //闹钟的分钟
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                //响铃时提示的信息
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                //用于指定该闹铃触发时是否振动
                .putExtra(AlarmClock.EXTRA_VIBRATE, true)
                //一个 content: URI，用于指定闹铃使用的铃声，也可指定 VALUE_RINGTONE_SILENT 以不使用铃声。
                //如需使用默认铃声，则无需指定此 extra。
                // .putExtra(AlarmClock.EXTRA_RINGTONE, ringtoneUri)
                //一个 ArrayList，其中包括应重复触发该闹铃的每个周日。
                // 每一天都必须使用 Calendar 类中的某个整型值（如 MONDAY）进行声明。
                //对于一次性闹铃，无需指定此 extra
                .putExtra(AlarmClock.EXTRA_DAYS, testDays)
                //如果为true，则调用startActivity()不会进入手机的闹钟设置界面
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void startTimer(String message, int seconds) {
        //action为AlarmClock.ACTION_SET_TIMER
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                //倒计总时长，以秒为单位
                .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                //是否要进入系统的倒计时设置界面
                .putExtra(AlarmClock.EXTRA_SKIP_UI, false);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    //public void init() {
    //  TimeThread thread = new TimeThread();
    //thread.start();
    // }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };


    @Override
    public void onClick (View v)  {
        switch (v.getId()) {
            case R.id.bot_3:
                //String a = editText.getText().toString().trim();
                if (spinner.getCount()==0){
                    Toast.makeText(this,"無活動信息",Toast.LENGTH_SHORT).show();
                }
                else {
                    int b = spinner.getSelectedItemPosition();
                    /*int c = a.length();*/
                    //int e=Integer.parseInt(a);
                    //System.out.println(e);
                    String timee=tt[b+1];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    System.out.println(timee);
                    String dd;
                    String HH;
                    String mm;

                    dd = timee.substring(8, 10);
                    HH = timee.substring(11, 13);
                    mm = timee.substring(14, 16);
                    int dd1;
                    dd1=Integer.parseInt(dd);
                    int kk=Integer.parseInt(HH);

                    kk=kk-1;
                    if(kk<0){
                        kk=23;
                        dd1--;
                    }

                    System.out.println(kk);
                    createAlarm(spinner.getSelectedItem().toString(),dd1, kk, Integer.parseInt(mm));
                    Toast.makeText(this,"闹钟已经设置",Toast.LENGTH_SHORT).show();


        /*      String b[] = new String[100];
                /*char y[]=new char[100];
                for(int k=0;k<c;k=k+1){
                    int x=0;
                    y[x]=a.charAt(k);
                    x++;
                }
                System.out.println(y[0]+y[1]+y[3]+y[4]);
                /*int d = 1;
                int f = 0;
                do {
                    char e = a.charAt(d - 1);
                    if (e != ',' && e != ' ') {
                        b[f] = b[f] + e;
                    } else {
                        f++;
                    }
                    d++;
                } while (d <= c);
                System.out.println(b[1]+ b[2]);*/
          /*      int i = 1;
                int x = 0;
                String g = b[x];
                /*sql_db = helper.getReadableDatabase();*/
        /*       Cursor cursor = sql_db.query("activitys_arrangement", null, null, null, null, null, null);
                if (cursor.getCount() == 0) {
                    Log.d("Activity", "no activities");
                } else {
                    cursor.moveToFirst();
                    for (; i <= b.length; i++) {
                        if (Integer.parseInt(g) == i) //判定？
                        {
                            String date = cursor.getString(2);
                            x++;

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dd;
                            String HH;
                            String mm;

                            dd = date.substring(9, 10);
                            HH = date.substring(12, 13);
                            mm = date.substring(15, 16);
                            createAlarm(Integer.parseInt(dd), Integer.parseInt(HH), Integer.parseInt(mm));
                            createAlarm(20, 9, 8);
                            Toast.makeText(this,"闹钟已经设置",Toast.LENGTH_SHORT).show();
                        } else {
                            cursor.moveToNext();

                        }
                    };

                    cursor.close();
                    sql_db.close();
                }*/
                }

                break;

            case R.id.back_menu:
                Intent intent = new Intent();
                intent.setClass(this,Main_menu.class);     //Main_menu为主界面class
                startActivity(intent);
                break;


        }

    }


    public void run(){
        try{
            while (true){
                SimpleDateFormat   formatter   =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
                Date curDate =  new Date(System.currentTimeMillis());
                //获取当前时间
                String str = formatter.format(curDate);
                handler.sendMessage(handler.obtainMessage(100,str));
                Thread.sleep(1000);//实时显示当前时间


                //将数据库中的数据放入listView中
                sql_activities helper=new sql_activities(alarm_setting.this);
                sql_db = helper.getReadableDatabase();
                Cursor cursor = sql_db.query("activitys_arrangement",null,null,null,null,null,null);
                if(cursor.getCount() == 0) {
                    Intent intent = new Intent();
                    intent.setClass(this,Main_menu.class);     //Main_menu为主界面class
                    startActivity(intent);
                    break;
                }
                else {
                    act_list = new ArrayList<Activity>();
                    name = new ArrayList();
                    cursor.moveToFirst();



                    do {
                        String num = cursor.getString(0);
                        String names = cursor.getString(1);
                        String time = cursor.getString(2);
                        String adress = cursor.getString(3);
                        String priority = cursor.getString(4);
                        /*String record = cursor.getString(5);*/

                        try{
                            //规定格式 (格式根据自己数据库取得的数据进行规范)
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //获取当前时间
                            Date date = new Date(System.currentTimeMillis());
                            String date3=sdf.format(date);

                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.HOUR,+24);
                            Date date24 = calendar.getTime();
                            String date4 = sdf.format(date24);
                            if((time.compareTo(String.valueOf(date3)))>0&&time.compareTo(date4)<0&&priority.equals("高")){
                                Activity activityOBJ = new Activity(num,names,time,adress,priority/*,null*/);
                                act_list.add(activityOBJ);
                                name.add(names+" "+time);
                                tt[tt1]=time;
                                tt1++;

                            }
                            //时间date类型 和 时间String类型
                                /*if(i<0&&priority=="高"){


                                    Activity activityOBJ = new Activity(num,names,time,adress,priority,record);
                                    act_list.add(activityOBJ);

                                }*/
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }while (cursor.moveToNext());


                    listView.setAdapter(new ActivityAdapter());
                    String[] strArray = (String[]) name.toArray(new String[name.size()]);
                    ArrayAdapter<String> namelist = new ArrayAdapter<>(alarm_setting.this, android.R.layout.simple_spinner_dropdown_item,strArray);
                    spinner.setAdapter(namelist);
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



    //适配器的设置
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
            View view = View.inflate(alarm_setting.this,R.layout.ningbao,null);

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
