package com.bloodbird.lifescheduler.Schedulers;

import com.bloodbird.lifescheduler.Models.Job;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseTest {
      Base test;


    @Before
    public void setUp() throws Exception {
        test=Base.getInstance();
        test.baseBuild();
        test.jobs.add(new Job("uc",3,5));
        test.jobs.add(new Job("bir",1,5));
        test.jobs.add(new Job("iki",2,5));

    }

    @Test
    public void baseBuild() {
    }

    @Test
    public void clockPulse() {

    }

    @Test
    public void getProgramTime() {
    }
}