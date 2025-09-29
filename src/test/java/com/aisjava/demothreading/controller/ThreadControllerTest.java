package com.aisjava.demothreading.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ThreadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public void runTasks_shouldReturnListOfResults() throws Exception {
        // Perform async request
        var mvcResult = mockMvc.perform(get("/run-tasks")
                        .param("count", "2")
                        .param("sleepMs", "200"))
                .andExpect(request().asyncStarted())   // verify async started
                .andReturn();
        // Wait for async result
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", containsString("Task")))
                .andExpect(jsonPath("$[1]", containsString("Task")));
    }

    @Test
    void testControllerTasks() throws Exception {
        runTasks_shouldReturnListOfResults(); // for normal test execution
    }
}
