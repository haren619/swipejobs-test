package com.swipejobs.test.matcher.web;

import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatcherController {

    @Autowired
    MatchingService matchingService;

    @GetMapping("/matcher/{workerId}")
    public List<Job> getMatchingJobs(@PathVariable Integer workerId){
        return matchingService.getMatchingJobs(workerId);
    }

}
