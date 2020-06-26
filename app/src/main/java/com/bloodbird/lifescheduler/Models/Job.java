package com.bloodbird.lifescheduler.Models;

import android.annotation.SuppressLint;
import android.util.Log;

import com.bloodbird.lifescheduler.Schedulers.Base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Job {
   private String name;
   private int priority;
   private  int time;
   private boolean status;
   private String birthday;   // for real time version
    private int  birthStamp; //for button iterative version
   private double score=0;


    public Job(String name, int priority, int time) {
        this.name = name;
        this.priority = priority;
        this.time = time;
        this.status=false;
        this.birthday=getMyBirthday();
        this.birthStamp= Base.getProgramTime();
    }

   private String getMyBirthday(){
       @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
       Date date = new Date();
       return  dateFormat.format(date).toLowerCase();
   }

    private boolean iskDateValid(String date){
        try
        {
            // checking valid integer using parseInt() method
            Integer.parseInt(date);
            return  true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }


    }

    public int getBirthStamp() {
        return birthStamp;
    }

    public void setBirthStamp(int birthStamp) {
        this.birthStamp = birthStamp;
    }
    @Deprecated
   public int getMyAge(){
       int birthD=  Integer.parseInt(this.birthday);
       int currentTime=Integer.parseInt(getMyBirthday());
        return currentTime-birthD;
   }

    public int getMyAgeStamp(){
        return Base.getProgramTime()-birthStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public boolean setPriority(int priority) {

        if(priority<=5 && priority>0){
            this.priority = priority;
            return true;
        }else{
            Log.d("lifescheduler","priority must be in range 1-5");
            this.priority = 1;
            return false;
        }


    }

    public int getTime() {
        return time;
    }

    public boolean setTime(int time) {
        if(time>0){
            this.time = time;
            return true;
        }else{
            Log.d("lifescheduler","time can not be negative");
            return false;
        }

    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBirthday() {
        return birthday;
    }

    public boolean setBirthday(String birthday) {
        if(iskDateValid(birthday)){
            this.birthday = birthday;
            return true;
        }else{
            Log.d("lifescheduler","date can not parsable");
            return  false;
        }

    }


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
