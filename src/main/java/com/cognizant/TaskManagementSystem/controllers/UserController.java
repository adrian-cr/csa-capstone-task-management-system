package com.cognizant.TaskManagementSystem.controllers;

import com.cognizant.TaskManagementSystem.exceptions.InvalidRequestException;
import com.cognizant.TaskManagementSystem.exceptions.NotFoundException;
import com.cognizant.TaskManagementSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired private UserService us;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(us.getAllUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.hasUserId(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id) {
        if (us.getUser(id)==null) throw new NotFoundException();
        return ResponseEntity.ok(us.getUser(id));
    }

    @PreAuthorize("@userSecurity.hasUserId(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
        @PathVariable("id") Integer userId,
        @RequestParam(name="username", required = false) String username,
        @RequestParam(name="password", required = false) String password
    ) {
        if (username==null && password==null) {
            throw new InvalidRequestException("At least one valid field must be provided.");
        }
        us.updateUser(userId, username, password);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #username.equals(authentication.name)")
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "username") String username) {
        if (us.deleteUser(username)==null) throw new NotFoundException();
        return ResponseEntity.ok("Username " + username + " deleted." );
    }
}




