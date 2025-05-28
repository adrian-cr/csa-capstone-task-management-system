package com.cognizant.TaskManagementSystem;

import com.cognizant.TaskManagementSystem.controllers.LoginController;
import com.cognizant.TaskManagementSystem.dto.LoginRequest;
import com.cognizant.TaskManagementSystem.models.User;
import com.cognizant.TaskManagementSystem.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskManagementSystemApplicationTests {
	
	private UserService userService;
	private LoginController loginController;

	@BeforeEach
	void setUp() {
		userService = mock(UserService.class);
		loginController = mock(LoginController.class);
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
		
		doReturn(mockResponse)
						.when(loginController)
						.login(any(LoginRequest.class), isNull());
		
		ResponseEntity<?> loginResponse = loginController.login(request, null);
		assertNotNull(loginResponse);
		String token = ((Map<String,String>) loginResponse.getBody()).get("token");
		
		assertEquals("dummy-token", token);
	}
	
	
}
