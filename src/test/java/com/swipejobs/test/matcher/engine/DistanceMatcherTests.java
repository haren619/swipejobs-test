package com.swipejobs.test.matcher.engine;

import com.swipejobs.test.matcher.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DistanceMatcherTests {

    private List<Job> jobs;
    private Worker worker;
    private DistanceMatcher distanceMatcher;

    @BeforeEach
    public void setup() {
        distanceMatcher = new DistanceMatcher();
        Location jobLocation1 = new Location(11.11, 11.11);
        Location jobLocation2 = new Location(-11.11, -11.11);
        Location jobLocation3 = new Location(11.21, 11.21);
        Location jobLocation4 = new Location(11.17, 11.17);

        Job job1 = new Job();
        job1.setLocation(jobLocation1);
        job1.setJobId(1);

        Job job2 = new Job();
        job2.setLocation(jobLocation2);
        job1.setJobId(2);

        Job job3 = new Job();
        job3.setLocation(jobLocation3);
        job1.setJobId(3);

        Job job4 = new Job();
        job4.setLocation(jobLocation4);
        job1.setJobId(4);

        worker = new Worker();
        JobSearchAddress address = new JobSearchAddress("km", 10, 11.11, 11.11);
        worker.setJobSearchAddress(address);
        worker.setUserId(1);

        jobs = Arrays.asList(job1, job2, job3, job4);
    }

    @Test
    public void testZeroDistanceToJob() {
        MatchingResult result = distanceMatcher.evaluate(worker, jobs.get(0));
        Assertions.assertTrue(result.isMatch());
        Assertions.assertEquals(3, result.getScore());
    }

    @Test
    public void testMaxDistanceExceeds() {
        MatchingResult result = distanceMatcher.evaluate(worker, jobs.get(1));
        Assertions.assertFalse(result.isMatch());
        Assertions.assertEquals(0, result.getScore());
    }

    @Test
    public void testDistanceScore1() {
        MatchingResult result = distanceMatcher.evaluate(worker, jobs.get(2));
        Assertions.assertTrue(result.isMatch());
        Assertions.assertEquals(1, result.getScore());
    }

    @Test
    public void testDistanceScore2() {
        MatchingResult result = distanceMatcher.evaluate(worker, jobs.get(3));
        Assertions.assertTrue(result.isMatch());
        Assertions.assertEquals(2, result.getScore());
    }
}
