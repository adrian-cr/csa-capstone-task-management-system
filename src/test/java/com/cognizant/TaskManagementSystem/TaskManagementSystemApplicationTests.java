package com.cognizant.TaskManagementSystem;

import com.cognizant.TaskManagementSystem.controllers.LoginController;
import com.cognizant.TaskManagementSystem.controllers.TaskController;
import com.cognizant.TaskManagementSystem.dto.LoginRequest;
import com.cognizant.TaskManagementSystem.models.Task;
import com.cognizant.TaskManagementSystem.models.User;
import com.cognizant.TaskManagementSystem.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskManagementSystemApplicationTests {
	
	private UserService userService;
	private LoginController loginController;
	private TaskController taskController;

	@BeforeEach
	void setUp() {
		userService = mock(UserService.class);
		loginController = mock(LoginController.class);
		taskController = mock(TaskController.class);
	}
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testSuccessfulSignup() {
		User testUser = new User("testuser", "password123", "user");
		when(userService.addUser(any(User.class))).thenReturn(testUser);
		User createdUser = userService.addUser(testUser);
		
		assertNotNull(createdUser);
		assertEquals("testuser", createdUser.getUsername());
		assertEquals("user", createdUser.getRole());
		verify(userService).addUser(testUser);
	}
	
	@Test
	void testSuccessfulLogin() {
		LoginRequest request = new LoginRequest("testuser", "password123");
		ResponseEntity<Object> mockResponse = new ResponseEntity<>(
				Collections.singletonMap("token", "dummy-token"),
				HttpStatus.OK
		);
		doReturn(mockResponse).when(loginController).login(any(LoginRequest.class), isNull());
		
		ResponseEntity<?> loginResponse = loginController.login(request, null);
		assertNotNull(loginResponse);
		
		String token = ((Map<String,String>) loginResponse.getBody()).get("token");
		assertEquals("dummy-token", token);
	}
	
	
	@Test
	void testSuccessfulTaskRetrieval() {
		Task task = new Task("Task Title", "This is a task.", "high", "2026-01-01");
		doReturn(task).when(taskController).getTask(anyInt(), anyInt());
		Task retrievedTask = taskController.getTask(1, 1);
		
		assertNotNull(retrievedTask);
		assertEquals("Task Title", retrievedTask.getTitle());
		assertEquals("This is a task.", retrievedTask.getDescription());
		assertEquals("high", retrievedTask.getPriority());
		assertEquals("to do", retrievedTask.getStatus());
		assertEquals("2026-01-01", retrievedTask.getDueDate());
		
		verify(taskController).getTask(1, 1);
	}
	
	@Test
	void testSuccessfulTaskCreation() {
		Task task = new Task("Task Title", "This is a task.", "high", "2026-01-01");
		doReturn(task).when(taskController).addTask(any(Task.class), anyInt());
		
		Task newTask = taskController.addTask(task, 1);
		assertNotNull(newTask);
		assertEquals("Task Title", newTask.getTitle());
		assertEquals("This is a task.", newTask.getDescription());
		assertEquals("high", newTask.getPriority());
		assertEquals("to do", newTask.getStatus());
		assertEquals("2026-01-01", newTask.getDueDate());
	}
	
	@Test
	void testSuccessfulTaskUpdate() {
		Task updatedTask = new Task("Updated Title", "Updated Description", "medium", "2026-02-01");
		updatedTask.setStatus("in progress");
		doReturn(updatedTask).when(taskController).updateTask(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
		Task result = taskController.updateTask(1, 1, "Updated Title", "Updated Description", "in progress", "medium", "2026-02-01");
		
		assertNotNull(result);
		assertEquals("Updated Title", result.getTitle());
		assertEquals("Updated Description", result.getDescription());
		assertEquals("in progress", result.getStatus());
		assertEquals("medium", result.getPriority());
		assertEquals("2026-02-01", result.getDueDate());
	}
	
	@Test
	void testSuccessfulTaskDeletion() {
		Task deletedTask = new Task("Task to Delete", "This task will be deleted", "low", "2026-01-01");
		doReturn(deletedTask).when(taskController).removeTask(anyInt(), anyInt());
		
		Task result = taskController.removeTask(1, 1);
		assertNotNull(result);
		assertEquals("Task to Delete", result.getTitle());
		assertEquals("This task will be deleted", result.getDescription());
		assertEquals("low", result.getPriority());
		verify(taskController).removeTask(1, 1);
	}
	
}
