package com.bloodbird.lifescheduler.Schedulers;

import android.content.Context;
import android.util.Log;

import com.bloodbird.lifescheduler.MainActivity;
import com.bloodbird.lifescheduler.Models.Job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public  class Base {
    public  List<Job> jobs;
    public  List<Job> listedJobs;
    private int programTime = 0;

    MainActivity context;

    private static Base single_instance = null;

    private Base()
    {

    }

    public static Base getInstance(){
        if (single_instance == null)
            single_instance = new Base();

        return single_instance;
    }

    public  void baseBuild(MainActivity context) {
        jobs = new ArrayList<>();
        listedJobs = new ArrayList<>();
        this.context=context;
    }

    // there will be a thread that count every hour in real time version.
    public  void clockPulse() {

        programTime++;
       listedJobs.clear();
       listedJobs.addAll(schedule(jobs)) ;
         context.notifyi();
    }

    private  List<Job> schedule(List<Job> jobs) {
        List<Job> sessionList = new ArrayList<>(jobs);

        for (int i = 0; i <  sessionList.size(); i++) {
            firstcalculate( sessionList.get(i));
        }
        sortJobs(sessionList);

        List<Job> splitedList = splitJobs(sessionList);
        Log.d("logd",splitedList.size()+"---------");

        for (int i = 0; i < splitedList.size(); i++) {

            calculateScore(splitedList.get(i));
        }
        sortJobs(splitedList);
        return splitedList;
    }

    public  int getProgramTime() {
        return programTime;
    }


    private  void calculateScore(Job job) {
        Log.d("logd",job.getPriority()+"*"+scoreNormalizeEquation(job.getBirthStamp())+":+"+job.getPriority() * scoreNormalizeEquation(job.getBirthStamp()));
        job.setScore(job.getPriority() * scoreNormalizeEquation(job.getBirthStamp()));
    }

    private  void firstcalculate(Job job) {
        job.setScore(job.getPriority());
    }



    /**
     * A feature written to increase the priority of aging jobs.
     * this multiplier is a sigmoid equation.
     *
     * @param age how much time passed from creating the task
     * @return normalized ageing multiplier
     */

    private  double scoreNormalizeEquation(int age) {
        Log.d("logd",age+"agea"+(23/ (1 + (Math.pow(Math.E, (-age + 5))))) + 1+"\n");
        return (23 / (1 + (Math.pow(Math.E, (-age + 5))))) + 1;
    }

    /**
     * Sort job list with score data.
     *
     * @param sessionList every session starts with clock pulse or time period pasts
     */
    private  void sortJobs(List<Job> sessionList) {
        Collections.sort(sessionList, new Comparator<Job>() {
            @Override
            public int compare(Job job, Job t1) {
                return (int) ( t1.getScore()-job.getScore() );
            }
        });
    }

    /**
     * Split all jobs to one piece.it is for preemptive scheduling feature.
     *
     * @param jobs job list
     * @return splitted job list
     */
    private  List<Job> splitJobs(List<Job> jobs) {
        int localtime=1;
        List<Job> segmentedList = new ArrayList<>();

        for (int i = 0; i < jobs.size(); i++) {
            for (int j = 0; j < jobs.get(i).getTime(); j++) {
                 Log.d("logd",j+"");
                Job temp = new Job(jobs.get(i).getName()+"-"+(j+1), jobs.get(i).getPriority(), 1,localtime);
                temp.setScore(jobs.get(i).getScore());
                segmentedList.add(temp);

            }
           // localtime+=jobs.get(i).getTime();

          //  jobs.get(i).setTime(jobs.get(i).getTime() - 1);
        }
        return segmentedList;


    }


    public void printList(){
         Log.d("logd",listedJobs.toString());
    }
}
