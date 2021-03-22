package com.swipejobs.test.matcher.engine;

import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.MatchingResult;
import com.swipejobs.test.matcher.model.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class QualificationMatcherTest {

    @Test
    public void testQualificationsMatch() {
        List<String> certificates = Arrays.asList("cert-1", "cert-2", "cert-3");

        Job job = new Job();
        job.setRequiredCertificates(certificates);
        job.setJobId(1);

        Worker worker = new Worker();
        worker.setCertificates(certificates);
        worker.setUserId(1);

        QualificationMatcher matcher = new QualificationMatcher();
        MatchingResult result = matcher.evaluate(worker, job);
        Assertions.assertTrue(result.isMatch());
        Assertions.assertEquals(1, result.getScore());
    }

    @Test
    public void testQualificationsDoNotMatch() {
        Job job = new Job();
        job.setRequiredCertificates(Arrays.asList("cert-1", "cert-2", "cert-3"));
        job.setJobId(1);

        Worker worker = new Worker();
        worker.setCertificates(Arrays.asList("cert-1", "cert-2"));
        worker.setUserId(1);

        QualificationMatcher matcher = new QualificationMatcher();
        MatchingResult result = matcher.evaluate(worker, job);
        Assertions.assertFalse(result.isMatch());
        Assertions.assertEquals(0, result.getScore());
    }

}
