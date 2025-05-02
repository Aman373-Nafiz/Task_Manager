package com.example.taskmanager.taskmanager_service.model;
import com.google.cloud.*;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}