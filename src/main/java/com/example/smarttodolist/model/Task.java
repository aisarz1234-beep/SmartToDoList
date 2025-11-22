package com.example.smarttodolist.model;
import java.time.LocalDate;
public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String category;
    private String priority;
    private boolean completed;

    public Task(String title, String description, LocalDate dueDate, String category, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.completed = false;
    }

    // setting for getters and setters
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public String getPriority() {return priority;}
    public void setPriority(String priority) {this.priority = priority;}

    public boolean isCompleted() {return completed;}
    public void setCompleted(boolean completed) {this.completed = completed;}

    @Override
    public String toString() {
        return title + " (" + category + ") - " + (completed ? "Completed" : "Pending");
    }
}
