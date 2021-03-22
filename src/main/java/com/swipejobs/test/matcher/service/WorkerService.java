package com.swipejobs.test.matcher.service;

import com.swipejobs.test.matcher.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    @Value("${swipejobs.workers.url}")
    private String workersResourceUrl;
    @Autowired
    private RestTemplate restTemplate;

    public Map<Integer, Worker> getWorkers() {
        ResponseEntity<Worker[]> response
                = restTemplate.getForEntity(workersResourceUrl, Worker[].class);
        Worker[] jobArr = response.getBody();
        //currently return a Map as api doesn't support query parameters
        return Arrays.asList(jobArr)
                .stream()
                .collect(Collectors.toMap(Worker::getUserId, Function.identity()));
    }

    public void setWorkersResourceUrl(String workersResourceUrl) {
        this.workersResourceUrl = workersResourceUrl;
    }

}
