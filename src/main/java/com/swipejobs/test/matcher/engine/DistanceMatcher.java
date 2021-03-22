package com.swipejobs.test.matcher.engine;

import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.MatchingResult;
import com.swipejobs.test.matcher.model.Worker;
import com.swipejobs.test.matcher.util.DistanceCalculator;
import org.springframework.stereotype.Component;

@Component
public class DistanceMatcher implements Matcher {

    @Override
    public MatchingResult evaluate(Worker worker, Job job) {
        double maxDistance = worker.getJobSearchAddress().getMaxJobDistance().doubleValue();

        double distanceToJob = getDistanceToJob(worker, job);
        boolean evalResult = distanceToJob <= maxDistance;
        return new MatchingResult(evalResult, evalResult ? getScoreBasedOnDistance(maxDistance, distanceToJob) : 0);
    }

    /* Get the distance between worker and job. */
    /* Used library class obtained from https://www.geodatasource.com/developers/java */
    private double getDistanceToJob(Worker worker, Job job) {
        double lat1 = job.getLocation().getLatitude();
        double lon1 = job.getLocation().getLongitude();
        double lat2 = worker.getJobSearchAddress().getLatitude();
        double lon2 = worker.getJobSearchAddress().getLongitude();
        String unit = worker.getJobSearchAddress().getUnit();

        return Math.abs(DistanceCalculator.distance(lat1, lon1, lat2, lon2, unit));
    }

    /* This is a simple score calculator based on distance to job. */
    /* Should be improved  in future to provide more granular score rating. */
    private Integer getScoreBasedOnDistance(double maxDistance, double distance) {
        double relativeDistance = (maxDistance - distance) / maxDistance;
        if (relativeDistance >= 0 && relativeDistance < 0.33d) {
            return 1;
        } else if (relativeDistance >= 0.33d && relativeDistance < 0.66d) {
            return 2;
        } else {
            return 3;
        }
    }
}