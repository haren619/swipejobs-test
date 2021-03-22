package com.swipejobs.test.matcher.engine;

import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.MatchingResult;
import com.swipejobs.test.matcher.model.Worker;
import org.springframework.stereotype.Component;

@Component
public class QualificationMatcher implements Matcher {
    @Override
    public MatchingResult evaluate(Worker worker, Job job) {
        boolean evalResult = worker.getCertificates().containsAll(job.getRequiredCertificates()) ;
        return new MatchingResult(evalResult, evalResult ? 1 : 0);
    }
}