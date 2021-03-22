package com.swipejobs.test.matcher.engine;

import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.model.MatchingResult;
import com.swipejobs.test.matcher.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchingEngine {

    @Autowired
    private List<Matcher> matchers;

    public Optional<Integer> getMatchingScore(Worker worker, Job job) {
        List<MatchingResult> matchingResults = matchers
                .stream()
                .map(matcher -> matcher.evaluate(worker, job))
                .collect(Collectors.toList());

        if (!matchingResults.stream().allMatch(MatchingResult::isMatch))
            return Optional.empty();

        return Optional.of(
                matchingResults.stream()
                        .filter(MatchingResult::isMatch)
                        .map(MatchingResult::getScore)
                        .reduce(0, Integer::sum));
    }

    public void setMatchers(List<Matcher> matchers) {
        this.matchers = matchers;
    }
}