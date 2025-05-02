package com.example.taskmanager.taskmanager_service.model;
import com.google.cloud.*;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String id;
    private String projectId;
    private String title;
    private String description;
    private status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum status{
        TO_DO,
        IN_PROGRESS,
        DONE
    }
}