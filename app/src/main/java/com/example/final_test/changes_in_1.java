package com.example.final_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class changes_in_1 extends AppCompatActivity implements View.OnClickListener{

    private EditText ac_nub,ac_names,ac_times,ac_address;
    private Button bot_update,bot_back,bot_out;
    private RadioGroup select_ac_priority;
    private sql_activities helper;
    private SQLiteDatabase sql_db;
    private String lite_1,lin_1;

    private int outs_passed=0;                              //判斷使否使用查看信息按鈕
    private String record_outs=" ";

    public String ac_priority=" ";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.changes);

        lite_1="activitys_arrangement";
        lin_1="num";

        ac_nub=findViewById(R.id.update_nub);
        ac_names=findViewById(R.id.update_name);
        ac_times=findViewById(R.id.update_date_time);
        ac_address=findViewById(R.id.update_address);

        select_ac_priority=findViewById(R.id.select_priority);
        select_ac_priority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                RadioButton radioButton = radioGroup.findViewById(i);
                ac_priority= (String) radioButton.getText();
            }
        });

        bot_update=findViewById(R.id.bot_5);
        bot_update.setOnClickListener(this);

        bot_back=findViewById(R.id.bot_00);
        bot_back.setOnClickListener(this);

        bot_out=findViewById(R.id.bot_outs);
        bot_out.setOnClickListener(this);

        helper=new sql_activities(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.bot_outs:
                String outs_nub=ac_nub.getText().toString().trim();
                // int ssin=Integer.parseInt(outs_nub);

                sql_db=helper.getWritableDatabase();


                Cursor ccin=sql_db.query(lite_1,null,"num=?",new String[]{outs_nub},null,null,null);

                if (ccin.getCount()==0)
                {
                    Toast.makeText(changes_in_1.this,"數據庫中還未添加活動數據",Toast.LENGTH_LONG).show();
                }
                else
                {
                    ccin.moveToFirst();
                    ac_names.setText(ccin.getString(1));
                    ac_times.setText(ccin.getString(2));
                    ac_address.setText(ccin.getString(3));
                    record_outs=ccin.getString(5);
                    outs_passed=1;
                    break;
                    //Toast.makeText(changes_in_1.this,outs_nub+ccin.getString(0),Toast.LENGTH_LONG).show();
                }

                ccin.close();
                sql_db.close();

                break;

            case R.id.bot_5:
                String delete_nubs=ac_nub.getText().toString().trim();
                String ac_na=ac_names.getText().toString().trim();
                String times=ac_times.getText().toString().trim();
                String ac_ad=ac_address.getText().toString().trim();

                if (outs_passed == 0)
                {
                    Toast.makeText(this,"未查看活動信息，請先查看活動信息！",Toast.LENGTH_LONG).show();
                    break;
                }

                if (delete_nubs == " " || ac_na == " " || times == " " || ac_ad == " " || ac_priority == " ")
                {
                    Toast.makeText(this,"活動信息不完整，請填完整活動信息！",Toast.LENGTH_LONG).show();
                    break;
                }

                sql_db=helper.getWritableDatabase();    //用讀寫方式打開數據庫
                int total_deleted=sql_db.delete(lite_1,"num=?",new String[]{delete_nubs});
                outs_passed=0;

                ContentValues values=new ContentValues();

                values.put("num",delete_nubs);
                values.put("names",ac_na);
                values.put("data_time",times);
                values.put("address",ac_ad);
                values.put("priority",ac_priority);
                values.put("record",record_outs);
                Long postion=sql_db.insert(lite_1,null,values);
                sql_db.close();
                Toast.makeText(this,"成功更改序號為"+delete_nubs+"的活動",Toast.LENGTH_LONG).show();

                ac_nub.setText(" ");
                ac_names.setText(" ");
                ac_times.setText(" ");
                ac_address.setText(" ");

                break;

            case R.id.bot_00:
                Intent intent = new Intent();
                intent.setClass(this,Activity_menu.class);     //MainActivity后面需要替代為主界面class
                startActivity(intent);
                finish();
                break;
        }

    }
}
