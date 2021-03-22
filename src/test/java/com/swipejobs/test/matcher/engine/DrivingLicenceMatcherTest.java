package com.swipejobs.test.matcher.engine;

import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.MatchingResult;
import com.swipejobs.test.matcher.model.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DrivingLicenceMatcherTest {

    @Test
    public void testDrivingLicenceMatch() {
        Job job = new Job();
        job.setDriverLicenseRequired(true);
        job.setJobId(1);

        Worker worker = new Worker();
        worker.setHasDriversLicense(true);
        worker.setUserId(1);

        DrivingLicenceMatcher matcher = new DrivingLicenceMatcher();
        MatchingResult result = matcher.evaluate(worker, job);
        Assertions.assertTrue(result.isMatch());
        Assertions.assertEquals(1, result.getScore());
    }

    @Test
    public void testDrivingLicenceDoesNotMatch() {
        Job job = new Job();
        job.setDriverLicenseRequired(true);
        job.setJobId(1);

        Worker worker = new Worker();
        worker.setHasDriversLicense(false);
        worker.setUserId(1);

        DrivingLicenceMatcher matcher = new DrivingLicenceMatcher();
        MatchingResult result = matcher.evaluate(worker, job);
        Assertions.assertFalse(result.isMatch());
        Assertions.assertEquals(0, result.getScore());
    }

}
