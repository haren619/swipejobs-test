package com.swipejobs.test.matcher.service;

import com.swipejobs.test.matcher.model.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
public class WorkerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WorkerService workerService;

    @Test
    public void testGetWorkers() {
        Worker worker = new Worker();
        worker.setUserId(1);
        Worker[] workerArr = {worker};
        Map<Integer, Worker> workerMap = new HashMap<>();
        workerMap.put(worker.getUserId(), worker);
        String url = "http://localhost:8080/test";

        workerService.setWorkersResourceUrl(url);
        when(restTemplate.getForEntity(url, Worker[].class))
                .thenReturn(new ResponseEntity(workerArr, HttpStatus.OK));

        Assertions.assertEquals(workerMap, workerService.getWorkers());
    }
}
