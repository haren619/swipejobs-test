package com.swipejobs.test.matcher.service;

import com.swipejobs.test.matcher.engine.MatchingEngine;
import com.swipejobs.test.matcher.exception.NotFoundException;
import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.JobSearchAddress;
import com.swipejobs.test.matcher.model.Location;
import com.swipejobs.test.matcher.model.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class MatchingServiceTest {

    private Worker worker;
    private List<Job> jobs;
    private HashMap<Integer, Worker> workersMap;
    @Mock
    private JobService jobService;
    @Mock
    private WorkerService workerService;
    @Mock
    private MatchingEngine matchingEngine;
    @InjectMocks
    private MatchingService matchingService;

    @BeforeEach
    public void setup() {
        matchingService.setMaximumMatchCount(3);
        workersMap = new HashMap<>();

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
        workersMap.put(worker.getUserId(), worker);
    }

    @Test
    public void testMatchingJobs() {
        when(workerService.getWorkers()).thenReturn(workersMap);
        when(jobService.getJobs()).thenReturn(jobs);
        when(matchingEngine.getMatchingScore(worker, jobs.get(0))).thenReturn(Optional.of(5));
        when(matchingEngine.getMatchingScore(worker, jobs.get(1))).thenReturn(Optional.of(4));
        when(matchingEngine.getMatchingScore(worker, jobs.get(2))).thenReturn(Optional.of(6));
        when(matchingEngine.getMatchingScore(worker, jobs.get(3))).thenReturn(Optional.of(2));

        Job[] expected = {jobs.get(2), jobs.get(0), jobs.get(1)};
        Assertions.assertArrayEquals(expected, matchingService.getMatchingJobs(1).toArray());
    }

    @Test
    public void testNotFoundException() {
        when(workerService.getWorkers()).thenReturn(new HashMap<>());

        Assertions.assertThrows(NotFoundException.class, () -> {
            matchingService.getMatchingJobs(1);
        });
    }
}
