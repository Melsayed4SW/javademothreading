package com.aisjava.demothreading.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Async("taskExecutor")
    public CompletableFuture<String> runTask(int id, long sleepMillis) {
        String thread = Thread.currentThread().getName();
        log.info("Task {} START on thread {}", id, thread);
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("Task " + id + " interrupted");
        }
        log.info("Task {} END on thread {}", id, thread);
        return CompletableFuture.completedFuture("Task " + id + " done by " + thread);
    }
}
