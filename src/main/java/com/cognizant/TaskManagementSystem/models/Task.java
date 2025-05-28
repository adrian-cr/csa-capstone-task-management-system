package com.cognizant.TaskManagementSystem.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="tasks")
public class Task {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="task_id", nullable=false)
    private Integer id;
   @Column(nullable=false)
    private String title;
    private String description;
   @Column(nullable=false)
    private String status;
   @Column()
    private String priority;
   @Column(name="due_date")
    private LocalDate dueDate;

    public Task() {
        this.status = "to do";
    }

    public Task(String title, String description, String priority, String dueDate) {
        this.title = title;
        this.description = description;
        this.status = "to do";
        this.priority = priority;
        this.dueDate = LocalDate.parse(dueDate);
    }

    public String getDueDate() {
        return dueDate.toString();
    }

    public void setDueDate(String dueDate) {
        this.dueDate = LocalDate.parse(dueDate);
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
            "Task{" +
            "id: " + id +
            ", title: " + title +
            ", description: " + description +
            ", status: " + status +
            ", priority: " + priority +
            ", dueDate: " + dueDate +
            '}';
    }
}
