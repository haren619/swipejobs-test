package com.swipejobs.test.matcher.web;

import com.swipejobs.test.matcher.exception.NotFoundException;
import com.swipejobs.test.matcher.model.Job;
import com.swipejobs.test.matcher.service.MatchingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class MatcherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchingService matchingService;

    @Test
    public void testMatcherReturnJobs() throws Exception {
        Job job1 = new Job();
        job1.setJobId(1);
        Job job2 = new Job();
        job2.setJobId(2);
        given(matchingService.getMatchingJobs(any())).willReturn(Arrays.asList(job1, job2));

        mockMvc.perform(MockMvcRequestBuilders.get("/matcher/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].jobId").value(1))
                .andExpect(jsonPath("$.[1].jobId").value(2));
    }

    @Test
    public void testMatcherReturnNoMatchingJobs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/matcher/0"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testMatcherReturnNotFound() throws Exception {
        given(matchingService.getMatchingJobs(any())).willThrow(new NotFoundException("Not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/match/0"))
                .andExpect(status().isNotFound());
    }

}
