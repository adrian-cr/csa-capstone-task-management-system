package com.cognizant.TaskManagementSystem.controllers;

import com.cognizant.TaskManagementSystem.models.Task;
import com.cognizant.TaskManagementSystem.models.User;
import com.cognizant.TaskManagementSystem.services.TaskService;
import com.cognizant.TaskManagementSystem.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired TaskService ts;
    @Autowired UserService us;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<Task> getTasks() {
        return ts.getAllTasks();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.hasUserId(#userId)")
    @GetMapping("/{userId}")
    public List<Task> getAllUserTasks(@PathVariable("userId") Integer userId) {
        if (us.getUser(userId)==null) return null;
        return us.getUser(userId).getTasks();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (@userSecurity.hasUserId(#userId) and @userSecurity.taskBelongsToUser(#userId, #taskId))")
    @GetMapping("/{userId}/{taskId}")
    public Task getTask(@PathVariable("userId") Integer userId, @PathVariable("taskId") Integer taskId) {
        if (us.getUser(userId)==null) return null;
        if (ts.getTask(taskId)==null) return null;
        return ts.getTask(taskId);
    }

    @PreAuthorize("@userSecurity.hasUserId(#userId)")
    @PostMapping("/{userId}")
    public Task addTask(@RequestBody Task task, @PathVariable("userId") Integer userId) {
        if (us.getUser(userId)==null) return null;
        User user = us.getUser(userId);
        user.addTask(task);
        return ts.addTask(task);
    }
    @PreAuthorize("@userSecurity.hasUserId(#userId) and @userSecurity.taskBelongsToUser(#userId, #taskId)")
    @PutMapping("/{userId}/{taskId}")
    public Task updateTask(
        @PathVariable("taskId") Integer taskId,
        @PathVariable("userId") Integer userId,
        @RequestParam(name="title", required=false) String title,
        @RequestParam(name="description", required=false) String description,
        @RequestParam(name="status", required=false) String status,
        @RequestParam(name="priority", required=false) String priority,
        @RequestParam(name="dueDate", required=false) String dueDate
    ) {
        if (ts.getTask(taskId)==null) return null;
        return ts.updateTask(taskId, title, description, status, priority, dueDate);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (@userSecurity.hasUserId(#userId) and @userSecurity.taskBelongsToUser(#userId, #taskId))")
    @DeleteMapping("/{userId}/{taskId}")
    public Task removeTask(
        @PathVariable("userId") Integer userId,
        @PathVariable("taskId") Integer taskId
    ){
        if (us.getUser(userId)==null || ts.getTask(taskId)==null) return null;
        User user = us.getUser(userId);
        Task task = ts.getTask(taskId);
        user.removeTask(task);
        return ts.deleteTask(taskId);
    }
}
