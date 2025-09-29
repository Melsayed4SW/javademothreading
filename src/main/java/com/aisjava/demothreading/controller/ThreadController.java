package com.aisjava.demothreading.controller;

import com.aisjava.demothreading.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class ThreadController {

    private final TaskService taskService;

    public ThreadController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/run-tasks")
    public CompletableFuture<ResponseEntity<List<String>>> runTasks(
            @RequestParam(defaultValue = "3") int count,
            @RequestParam(defaultValue = "2000") long sleepMs) {

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            futures.add(taskService.runTask(i, sleepMs));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<String> results = futures.stream().map(CompletableFuture::join).toList();
                    return ResponseEntity.ok(results);
                });
    }
}
