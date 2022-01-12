package com.example.sql_light_demo;

public class Activity {
    private String name;
    private String num;
    private String time;
    private String address;
    private String priority;
    private String record;

    public Activity(String name,String time){
        this.name = name;
        this.time = time;
        this.address = null;
        this.priority = null;
        this.record=null;
    }

    public Activity(String num, String name, String time, String address, String priority, String record){
        this.num = num;
        this.name = name;
        this.time = time;
        this.address = address;
        this.priority = priority;
        this.record=record;
    }

    public Activity(String num, String name, String time, String address, String priority/*,String record*/){
        this.num = num;
        this.name = name;
        this.time = time;
        this.address = address;
        this.priority = priority;
        /*this.record=record;*/
    }

    public Activity(String time){
        this.name = null;
        this.time = time;
        this.address = null;
        this.priority = null;
        this.record=null;
    }

    public void Activity(){
        this.name = null;
        this.time = null;
        this.address = null;
        this.priority = null;
        this.record=null;
    }

    public String getName(){
        return name;
    }

    public String getNum(){
        return num;
    }

    public String getTime(){ return time; }

    public String getAdress(){ return address; }

    public String getPriority(){ return priority; }

    public String getRecord(){ return record;}
}
