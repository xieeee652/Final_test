package com.example.sql_light_demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class aql_users extends SQLiteOpenHelper {
    public aql_users(Context context)      //指定数据库文件名和版本号，用于打开或者创建一个数据库
    {
        super(context,"users_lite",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql_1="Create TABLE Users(name VARCHAR(100),num_starts INTEGER,num_lose INTEGER)";
        db.execSQL(sql_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
                                    //当打开数据库时，发现指定版本号改变了，要执行的操作
    {

    }
}
