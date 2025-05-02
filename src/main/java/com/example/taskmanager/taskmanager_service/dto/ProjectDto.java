package com.example.taskmanager.taskmanager_service.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String id;

    @NotBlank(message = "Project name is required")
    private String name;

    private String description;
}