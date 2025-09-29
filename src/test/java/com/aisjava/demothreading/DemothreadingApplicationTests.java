package com.aisjava.demothreading;


import com.aisjava.demothreading.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class DemothreadingApplicationTests {

	@Autowired
	private TaskService taskService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void runTask_shouldCompleteSuccessfully() throws Exception {
		CompletableFuture<String> result = taskService.runTask(1, 500);
		String output = result.get(2, TimeUnit.SECONDS); // wait max 2s
		assertThat(output).contains("Task 1 done by");
	}

	@Test
	void runTasks_shouldReturnListOfResults() throws Exception {
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


}
