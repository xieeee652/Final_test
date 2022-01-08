package com.example.final_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DakaActivity extends AppCompatActivity implements View.OnClickListener {
    private sql_activities helper;
    private TextView current_time;
    private SQLiteDatabase sql_db;
    private List<Activity> daka_list,act_outs;
    private ListView listView;
    private Button btn_show,btn_1,btn_daka;
    private String str;
    private Date curDate;
    private TextView txt_date,num;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    private TextView a,b,c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daka);

        current_time = findViewById(R.id.txt_clock);
        listView = findViewById(R.id.cur_activity_list);
        btn_show = findViewById(R.id.show);
        btn_1=findViewById(R.id.button_1);
        txt_date=findViewById(R.id.date);
        num=findViewById(R.id.num);

        a = findViewById(R.id.textView3);
        b = findViewById(R.id.textView4);
        c = findViewById(R.id.textView5);

        btn_1.setOnClickListener(this);
        btn_show.setOnClickListener(this);

        listView.setAdapter(null);

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show:
            {
                String sqlname = "activitys_arrangement";
                boolean hasact = false;

                //將數據庫中的已經開始隻活動放入listView中
                helper=new sql_activities(this);
                sql_db = helper.getReadableDatabase();

                Cursor cursor = sql_db.query(sqlname,null, null, null, null,
                        null,null);
                cursor.moveToFirst();
                if(cursor.getCount() == 0) {
                    Toast.makeText(this,"數據庫中無活動",Toast.LENGTH_LONG).show();
                }
                else {
                    daka_list = new ArrayList<Activity>();
                    act_outs=new ArrayList<Activity>();
                    do {
                        String num = cursor.getString(0);
                        String names = cursor.getString(1);
                        String time = cursor.getString(2);
                        String adress = cursor.getString(3);
                        String priority = cursor.getString(4);
                        String record = cursor.getString(5);


                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //獲取當前時間
                        Date date = new Date(System.currentTimeMillis());
                        String cur_date = sdf.format(date);
                        String date_cur = cur_date.substring(0,10);
                        String date_act = time.substring(0,10);
                        if((date_cur.compareTo(String.valueOf(date_act))) == 0  ){
                            if(record.compareTo("0")==0){
                                Activity activityOBJ = new Activity(num,names,time,adress,priority,record);
                                daka_list.add(activityOBJ);
                            }
                            hasact = true;
                        }




                    }while (cursor.moveToNext());


                    int counts_act=daka_list.size();
                    String min = daka_list.get(0).getTime();
                    for (int i=1;i<counts_act;i++)
                    {
                        if (min.compareTo(daka_list.get(i).getTime())>0){
                            min = daka_list.get(i).getTime();
                            Activity t;
                            t = daka_list.get(0);
                            daka_list.set(0, daka_list.get(i));
                            daka_list.set(i, t);
                        }

                    }
                    act_outs.add(daka_list.get(0));
                    if(hasact)
                        if (daka_list.size() == 0){
                            Toast.makeText(this,"你已經完成當日所有打卡活動",Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(this,"下一個活動如上",Toast.LENGTH_LONG).show();

                    else Toast.makeText(this,"當日無打卡活動，請擇日再來！",Toast.LENGTH_LONG).show();


                    listView.setAdapter(new ActivityAdapter());
                    cursor.close();
                    sql_db.close();
                    break;
                }
            }


            case R.id.button_1:

                Intent intent = new Intent();
                intent.setClass(this,Main_menu.class);     //Main_menu为主界面class
                startActivity(intent);
                finish();
                break;
        }
    }


    public void init() {
        TimeThread thread = new TimeThread();
        thread.start();

    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            current_time.setText((String) msg.obj);
        }
    };


    class TimeThread extends Thread{
        public void run(){
            try{
                while (true){
                    curDate = new Date(System.currentTimeMillis());
                    //获取当前时间
                    str = formatter.format(curDate);
                    handler.sendMessage(handler.obtainMessage(100,str));
                    Thread.sleep(1000);//实时显示当前时间
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    class ActivityAdapter extends BaseAdapter implements View.OnClickListener {

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
        public View getView(int position, View convertView, ViewGroup parent) {//將position所指的數據填入到ListView中

            View view = View.inflate(DakaActivity.this,R.layout.list_item_daka,null);

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

            Button btn_daka = view.findViewById(R.id.daka);
            btn_daka.setOnClickListener(this);


            return view;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.daka:
                    TextView act_date = findViewById(R.id.date);
                    TextView act_id = findViewById(R.id.num);

                    String time = act_date.getText().toString().trim();
                    String id = act_id.getText().toString().trim();   //讀取num -->id

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //獲取當前時間


                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE,-30);
                    Date end_time = calendar.getTime();

                    Date date = new Date(System.currentTimeMillis());
                    String cur_date = sdf.format(date);
                    String end_date = sdf.format(end_time);
                    String daka_date = time;


                    //獲取當前時間

                    try {
                        if ((cur_date.compareTo(String.valueOf(daka_date)) < 0)) {//比較兩個時間，如未超過打卡時間就顯示Toast
                            Toast.makeText(DakaActivity.this, "打卡時間未到！", Toast.LENGTH_LONG).show();
                        } else if ((cur_date.compareTo(String.valueOf(daka_date))> 0) && (daka_date.compareTo(String.valueOf(end_date)) > 0)) {
                            //到打卡時間就顯示打卡成功並且將數據庫中目前活動的record變為1
                            sql_db = helper.getReadableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("record", "1");
                            sql_db.update("activitys_arrangement", contentValues, "num = ?", new String[]{id});
                            Toast.makeText(DakaActivity.this, "打卡成功！", Toast.LENGTH_LONG).show();
                        } else if ((daka_date.compareTo(String.valueOf(end_date)) < 0)) {//超過打卡時間後就顯示打卡超時
                            Toast.makeText(DakaActivity.this, "打卡超時！", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        System.out.print("打卡錯誤");
                        e.printStackTrace();
                    }
                    break;
            }
        }
        class ViewHolder{
            TextView time;
            Button daka;
        }
    }


}

