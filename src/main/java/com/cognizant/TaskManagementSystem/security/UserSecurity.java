package com.cognizant.TaskManagementSystem.security;

import com.cognizant.TaskManagementSystem.models.User;
import com.cognizant.TaskManagementSystem.services.TaskService;
import com.cognizant.TaskManagementSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {
    @Autowired private UserService us;
    @Autowired private TaskService ts;

    public boolean hasUserId(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = us.getUserByUsername(username);
        return user != null && user.getId().equals(id);
    }
    
    public boolean taskBelongsToUser(Integer userId, Integer taskId) {
        User user = us.getUser(userId);
        return user!=null && user.getTasks().stream()
                .anyMatch(task -> task.getId().equals(taskId));
    }

}
