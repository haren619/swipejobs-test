package com.swipejobs.test.matcher.service;

import com.swipejobs.test.matcher.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class JobService {

    @Value("${swipejobs.jobs.url}")
    private String jobsResourceUrl;
    @Autowired
    private RestTemplate restTemplate;

    public List<Job> getJobs() {
        ResponseEntity<Job[]> response
                = restTemplate.getForEntity(jobsResourceUrl, Job[].class);
        Job[] jobArr = response.getBody();
        return Arrays.asList(jobArr);
    }

    public void setJobsResourceUrl(String jobsResourceUrl) {
        this.jobsResourceUrl = jobsResourceUrl;
    }
}
