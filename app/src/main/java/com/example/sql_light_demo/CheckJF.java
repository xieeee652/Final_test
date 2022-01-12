package com.example.sql_light_demo;

public class CheckJF {
    String CH;
    public String CJF(int JF){
        if(JF>2)CH="加把勁啊！";
        if(JF>6)CH="幹的針不戳！";
        if(JF<=2)CH="你在幹什麽？就這？";
        return CH;
    }
}
