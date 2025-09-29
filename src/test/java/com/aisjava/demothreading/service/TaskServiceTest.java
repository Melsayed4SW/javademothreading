package com.aisjava.demothreading.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;


    public void runTask_shouldCompleteSuccessfully() throws Exception {
        CompletableFuture<String> result = taskService.runTask(1, 500);
        String output = result.get(2, TimeUnit.SECONDS); // wait max 2s
        assertThat(output).contains("Task 1 done by");
    }

    @Test
    void testServiceTask() throws Exception {
        runTask_shouldCompleteSuccessfully(); // for normal test execution
    }
}
