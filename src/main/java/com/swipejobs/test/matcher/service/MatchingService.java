package com.swipejobs.test.matcher.service;

import com.swipejobs.test.matcher.engine.MatchingEngine;
import com.swipejobs.test.matcher.exception.NotFoundException;
import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.MatchingJob;
import com.swipejobs.test.matcher.model.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MatchingService {

    @Autowired
    private JobService jobService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private MatchingEngine matchingEngine;


    @Value("${swipejobs.max.match.count}")
    private Integer maximumMatchCount;

    public List<Job> getMatchingJobs(final Integer workerId) {
        log.debug("Find matching jobs for workerId: {}", workerId);
        final Worker worker = workerService.getWorkers().get(workerId);
        if (Objects.isNull(worker)) {
            log.debug("Worker not found for id: {}", workerId);
            throw new NotFoundException(String.format("Worker not found for id: %d", workerId));
        }
        final List<Job> jobs = jobService.getJobs();

        List<MatchingJob> matchingJobsWithScore = getMatchingJobsWithScore(jobs, worker);

        // sort by matching score
        // future improvement - use billing billing rate to sort
        Comparator<MatchingJob> scoreComparator = Comparator.comparing(MatchingJob::getScore);

        return matchingJobsWithScore.stream()
                .sorted(scoreComparator.reversed())
                .limit(maximumMatchCount)
                .map(MatchingJob::getJob)
                .collect(Collectors.toList());
    }

    private List<MatchingJob> getMatchingJobsWithScore(final List<Job> jobs, final Worker worker) {
        List<MatchingJob> matchingJobs = new ArrayList<>();
        jobs.forEach(job -> {
            Optional<Integer> matchingScore = matchingEngine.getMatchingScore(worker, job);
            if (matchingScore.isPresent()) {
                Integer score = matchingScore.get();
                log.debug("Matching score for jobId: {} is {}", job.getJobId(), score);
                matchingJobs.add(new MatchingJob(job, score));
            }
        });
        return matchingJobs;
    }

    public void setMaximumMatchCount(Integer maximumMatchCount) {
        this.maximumMatchCount = maximumMatchCount;
    }

}
