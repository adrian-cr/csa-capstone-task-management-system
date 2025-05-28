package com.cognizant.TaskManagementSystem.repositories;

import com.cognizant.TaskManagementSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
