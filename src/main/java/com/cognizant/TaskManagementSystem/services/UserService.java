package com.cognizant.TaskManagementSystem.services;

import com.cognizant.TaskManagementSystem.models.User;
import com.cognizant.TaskManagementSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService /*implements UserDetailsService*/ {
    @Autowired private UserRepository userRep;
    @Autowired private PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(user);
    }

    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    public User getUser(Integer id) {
        return userRep.findById(id).get();
    }

    public User getUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    public void updateUser(Integer userId, String newUsername, String password) {
        if (userRep.findById(userId).isEmpty()) return;
        User user = userRep.findById(userId).get();
        if (user!=null) {
            if (newUsername!=null) user.setUsername(newUsername);
            if (password!=null) user.setPassword(passwordEncoder.encode(password));
            userRep.save(user);
        }
    }

    public User deleteUser(String username) {
        User user = userRep.findByUsername(username);
        if (user==null) return null;
        userRep.deleteById(user.getId());
        return user;
    }

}
