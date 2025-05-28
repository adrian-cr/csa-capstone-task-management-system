package com.cognizant.TaskManagementSystem.services;

import com.cognizant.TaskManagementSystem.models.Task;
import com.cognizant.TaskManagementSystem.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired TaskRepository taskRep;

    public List<Task> getAllTasks() {
        return taskRep.findAll();
    }
    public Task getTask(Integer id) {
        return taskRep.findById(id).get();
    }
    public Task addTask(Task task) {
        taskRep.save(task);
        return task;
    }
    public Task updateTask(
        Integer id,
        String title,
        String description,
        String status,
        String priority,
        String dueDate
    ) {
        if (taskRep.findById(id).isEmpty()) return null;

        Task task = taskRep.findById(id).get();
        if (title!=null) task.setTitle(title);
        if (description!=null) task.setDescription(description);
        if (status!=null) task.setStatus(status);
        if (priority!=null) task.setPriority(priority);
        if (dueDate!=null) task.setDueDate(dueDate);
        taskRep.save(task);
        return task;
    }

    public Task deleteTask(Integer id) {
        if (taskRep.findById(id).isEmpty()) return null;
        Task task = taskRep.findById(id).get();
        taskRep.deleteById(id);
        return task;
    }

}
