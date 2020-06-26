package com.bloodbird.lifescheduler.Schedulers;

import com.bloodbird.lifescheduler.Models.Job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public  class Base {
    public static List<Job> jobs;
    public static List<Job> listedJobs;
    private static int programTime = 0;


    public static void baseBuild() {
        jobs = new ArrayList<>();
        listedJobs = new ArrayList<>();

    }

    // there will be a thread that count every hour in real time version.
    public static void clockPulse() {
        if (programTime == 0) {
            programTime++;
            return;
        }
        programTime++;

        if (listedJobs.size() > 0)
            listedJobs.remove(0);

        listedJobs = splitJobs(schedule(jobs));


    }

    private static List<Job> schedule(List<Job> jobs) {
        List<Job> sessionList = new ArrayList<>(jobs);

        for (int i = 0; i < sessionList.size(); i++) {
            calculateScore(sessionList.get(i));
        }
        sortJobs(sessionList);
        return sessionList;
    }

    public static int getProgramTime() {
        return programTime;
    }


    private static void calculateScore(Job job) {
        job.setScore(job.getPriority() * scoreNormalizeEquation(job.getMyAgeStamp()));
    }

    /**
     * A feature written to increase the priority of aging jobs.
     * this multiplier is a sigmoid equation.
     *
     * @param age how much time passed from creating the task
     * @return normalized ageing multiplier
     */
    private static double scoreNormalizeEquation(int age) {
        return (4 / (1 + (Math.pow(Math.E, (-age + 4))))) + 1;
    }

    /**
     * Sort job list with score data.
     *
     * @param sessionList every session starts with clock pulse or time period pasts
     */
    private static void sortJobs(List<Job> sessionList) {
        Collections.sort(sessionList, new Comparator<Job>() {
            @Override
            public int compare(Job job, Job t1) {
                return (int) (job.getScore() - t1.getScore());
            }
        });
    }

    /**
     * Split all jobs to one piece.it is for preemptive scheduling feature.
     *
     * @param jobs job list
     * @return splitted job list
     */
    private static List<Job> splitJobs(List<Job> jobs) {

        List<Job> segmentedList = new ArrayList<>();

        for (int i = 0; i < jobs.size(); i++) {
            for (int j = 0; j < jobs.get(i).getTime(); j++) {

                Job temp = new Job(jobs.get(i).getName(), jobs.get(i).getPriority(), 1);
                temp.setScore(jobs.get(i).getScore());
                segmentedList.add(temp);
                jobs.get(i).setTime(jobs.get(i).getTime() - 1);
            }
        }
        return segmentedList;


    }


}
