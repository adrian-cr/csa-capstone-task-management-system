package com.cognizant.TaskManagementSystem.controllers;

import com.cognizant.TaskManagementSystem.exceptions.InvalidRequestException;
import com.cognizant.TaskManagementSystem.exceptions.NotFoundException;
import com.cognizant.TaskManagementSystem.models.Task;
import com.cognizant.TaskManagementSystem.models.User;
import com.cognizant.TaskManagementSystem.services.TaskService;
import com.cognizant.TaskManagementSystem.services.UserService;
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
        if (us.getUser(userId)==null) throw new NotFoundException();
        return us.getUser(userId).getTasks();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (@userSecurity.hasUserId(#userId) and @userSecurity.taskBelongsToUser(#userId, #taskId))")
    @GetMapping("/{userId}/{taskId}")
    public Task getTask(@PathVariable("userId") Integer userId, @PathVariable("taskId") Integer taskId) {
        if (us.getUser(userId)==null || ts.getTask(taskId)==null) throw new NotFoundException();
        return ts.getTask(taskId);
    }

    @PreAuthorize("@userSecurity.hasUserId(#userId)")
    @PostMapping("/{userId}")
    public Task addTask(@RequestBody Task task, @PathVariable("userId") Integer userId) {
        if (task.getTitle()==null || task.getDescription()==null || task.getPriority()==null || task.getDueDate()==null) throw new InvalidRequestException("All required fields must be provided.");
        if (us.getUser(userId)==null) throw new NotFoundException();
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
        if (title==null && description==null && status==null && priority==null && dueDate==null) throw new InvalidRequestException("At least one valid field must be provided.");
        if (us.getUser(userId)==null || ts.getTask(taskId)==null) throw new NotFoundException();
        return ts.updateTask(taskId, title, description, status, priority, dueDate);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (@userSecurity.hasUserId(#userId) and @userSecurity.taskBelongsToUser(#userId, #taskId))")
    @DeleteMapping("/{userId}/{taskId}")
    public Task removeTask(
        @PathVariable("userId") Integer userId,
        @PathVariable("taskId") Integer taskId
    ){
        if (us.getUser(userId)==null || ts.getTask(taskId)==null) throw new NotFoundException();
        User user = us.getUser(userId);
        Task task = ts.getTask(taskId);
        user.removeTask(task);
        return ts.deleteTask(taskId);
    }
}
