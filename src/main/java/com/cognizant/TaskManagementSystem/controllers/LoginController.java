package com.cognizant.TaskManagementSystem.controllers;

import com.cognizant.TaskManagementSystem.dto.LoginRequest;
import com.cognizant.TaskManagementSystem.exceptions.InvalidRequestException;
import com.cognizant.TaskManagementSystem.exceptions.UnauthorizedAccessException;
import com.cognizant.TaskManagementSystem.security.JwtUtil;
import com.cognizant.TaskManagementSystem.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;

    @GetMapping
    public ResponseEntity<?> getLoginPage() {
        return ResponseEntity.ok("Login Page");
    }

    @PostMapping
    public ResponseEntity<?> login(
            @RequestBody(required = false) LoginRequest request,
            @RequestHeader(name="Authorization", required = false) String authHeader
    ) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                return ResponseEntity.ok(new JwtResponse(token));
            }
            throw new UnauthorizedAccessException("Invalid token. Please login again.");
        }

        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            throw new InvalidRequestException("Username and password must be provided");
        }

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }

}
