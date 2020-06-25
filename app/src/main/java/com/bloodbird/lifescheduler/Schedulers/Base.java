package com.bloodbird.lifescheduler.Schedulers;

import com.bloodbird.lifescheduler.Models.Job;

import java.util.ArrayList;
import java.util.List;

public class Base {
   public static  List<Job> jobs;
   public static  List<Job> listedJobs;
   private static int programTime=0;


    public static void baseBuild(){
       jobs= new ArrayList<>();
       listedJobs= new ArrayList<>();

   }

     // there will be a thread that count every hour in real time version.
   public static void clockPulse(){
       programTime++;
      listedJobs= schedule(jobs);
   }

    private static List<Job> schedule(List<Job> jobs) {


        return jobs;
    }

    public static int getProgramTime() {
        return programTime;
    }


}
