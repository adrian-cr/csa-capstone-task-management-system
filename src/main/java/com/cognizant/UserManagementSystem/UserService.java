package com.cognizant.UserManagementSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    UserRepository userRep;

    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    public User getUser(Integer id) {
        if (userRep.existsById(id)) {
            return userRep.findById(id).get();
        }
        logger.warn("User not found.");
        return null;
    }

    public void addUser(User user) {
        userRep.save(user);
    }

    public void updateUser(Integer id, String username, String password, String role) {
        if (userRep.existsById(id)) {
            User user = userRep.findById(id).get();
            if (username != null) user.setUsername(username);
            if (password != null) user.setUsername(password);
            if (role != null) user.setUsername(role);
            userRep.save(user);
        }
    }

    public void deleteUser(Integer id) {
        userRep.deleteById(id);
    }

}
