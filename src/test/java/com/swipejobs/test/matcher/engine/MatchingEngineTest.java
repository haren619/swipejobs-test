package com.swipejobs.test.matcher.engine;

import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.MatchingResult;
import com.swipejobs.test.matcher.model.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MatchingEngineTest {

    private Job job;
    private Worker worker;
    @Mock
    private DistanceMatcher distanceMatcher;
    @Mock
    private DrivingLicenceMatcher drivingLicenceMatcher;
    @Mock
    private QualificationMatcher qualificationMatcher;
    @InjectMocks
    private MatchingEngine matchingEngine;

    @BeforeEach
    public void setup() {
        matchingEngine.setMatchers(Arrays.asList(distanceMatcher, drivingLicenceMatcher, qualificationMatcher));

        job = new Job();
        job.setJobId(1);

        worker = new Worker();
        worker.setUserId(1);
    }

    @Test
    public void testNoneMatch() {
        when(distanceMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(false, 0));
        when(drivingLicenceMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(false, 0));
        when(qualificationMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(false, 0));

        Optional<Integer> result = matchingEngine.getMatchingScore(worker, job);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    public void testAllMatch() {
        when(distanceMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(true, 1));
        when(drivingLicenceMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(true, 2));
        when(qualificationMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(true, 4));

        Optional<Integer> result = matchingEngine.getMatchingScore(worker, job);
        Assertions.assertEquals(Optional.of(7), result);
    }

    @Test
    public void testAnyNoneMatch() {
        when(distanceMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(false, 0));
        when(drivingLicenceMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(true, 2));
        when(qualificationMatcher.evaluate(any(), any())).thenReturn(new MatchingResult(true, 3));

        Optional<Integer> result = matchingEngine.getMatchingScore(worker, job);
        Assertions.assertEquals(Optional.empty(), result);
    }
}
