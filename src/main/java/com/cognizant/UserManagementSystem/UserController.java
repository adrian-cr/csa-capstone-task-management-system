package com.cognizant.UserManagementSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private UserService us;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = us.getAllUsers();
        if (users.isEmpty()) logger.warn("User list is empty.");
        return users;
    }

    @GetMapping(path="/{userId}")
    public User getUser(@PathVariable("userId") Integer id) {
        return us.getUser(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        if (user.getUsername()==null || user.getPassword()==null || user.getRole()==null) {
            logger.error("Incomplete request body.");
            return;
        }
        us.addUser(user);
    }

    @PutMapping(path="/{userId}")
    public void updateUser(
        @PathVariable("userId") Integer id,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("role") String role
    ) {
        if (username==null && password==null) {
            logger.warn("No valid fields to update.");
            return;
        }
       us.updateUser(id, username, password, role);
    }

    @DeleteMapping(path="/{userId}")
    public void deleteUser(@PathVariable("userId") Integer id) {
        if (us.getUser(id)==null) {
            logger.warn("User not found.");
            return;
        }
        us.deleteUser(id);
    }

}
