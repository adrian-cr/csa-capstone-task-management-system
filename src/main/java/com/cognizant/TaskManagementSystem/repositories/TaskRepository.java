package com.cognizant.TaskManagementSystem.repositories;

import com.cognizant.TaskManagementSystem.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}