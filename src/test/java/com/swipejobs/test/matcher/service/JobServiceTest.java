package com.swipejobs.test.matcher.service;

import com.swipejobs.test.matcher.model.Job;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@SpringBootTest
public class JobServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private JobService jobService;

    @Test
    public void testGetJobs() {
        Job job = new Job();
        job.setJobId(1);
        Job[] jobArr = {job};
        String url = "http://localhost:8080/test";

        jobService.setJobsResourceUrl(url);
        when(restTemplate.getForEntity(url, Job[].class))
                .thenReturn(new ResponseEntity(jobArr, HttpStatus.OK));

        Assertions.assertArrayEquals(jobArr, jobService.getJobs().toArray());
    }
}
