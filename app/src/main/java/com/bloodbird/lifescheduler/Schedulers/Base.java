package com.bloodbird.lifescheduler.Schedulers;

import android.util.Log;

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

    private static Base single_instance = null;

    private Base()
    {

    }

    public static Base getInstance(){
        if (single_instance == null)
            single_instance = new Base();

        return single_instance;
    }

    public  void baseBuild() {
        jobs = new ArrayList<>();
        listedJobs = new ArrayList<>();

    }

    // there will be a thread that count every hour in real time version.
    public  void clockPulse() {


        programTime++;

     /*   if (listedJobs.size() > 1)
            listedJobs.remove(0);
*/

        listedJobs = splitJobs(schedule(jobs));

        printList();


    }

    private  List<Job> schedule(List<Job> jobs) {
        List<Job> sessionList = new ArrayList<>(jobs);

        for (int i = 0; i < sessionList.size(); i++) {
            calculateScore(sessionList.get(i));
        }
        sortJobs(sessionList);
        return sessionList;
    }

    public  int getProgramTime() {
        return programTime;
    }


    private  void calculateScore(Job job) {
        job.setScore(job.getPriority() * scoreNormalizeEquation(job.getMyAgeStamp()));
    }

    /**
     * A feature written to increase the priority of aging jobs.
     * this multiplier is a sigmoid equation.
     *
     * @param age how much time passed from creating the task
     * @return normalized ageing multiplier
     */
    private  double scoreNormalizeEquation(int age) {
        return (4 / (1 + (Math.pow(Math.E, (-age + 4))))) + 1;
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

        List<Job> segmentedList = new ArrayList<>();

        for (int i = 0; i < jobs.size(); i++) {
            for (int j = 0; j < jobs.get(i).getTime(); j++) {

                Job temp = new Job(jobs.get(i).getName()+"-"+(j+1), jobs.get(i).getPriority(), 1);
                temp.setScore(jobs.get(i).getScore());
                segmentedList.add(temp);
                jobs.get(i).setTime(jobs.get(i).getTime() - 1);
            }
        }
        return segmentedList;


    }


    public void printList(){
         Log.d("logd",listedJobs.toString());
    }
}
