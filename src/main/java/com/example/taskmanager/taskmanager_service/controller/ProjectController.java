package com.example.taskmanager.taskmanager_service.controller;
import com.example.taskmanager.taskmanager_service.dto.ProjectDto;
import com.example.taskmanager.taskmanager_service.dto.response.ApiResponse;
import com.example.taskmanager.taskmanager_service.model.Project;
import com.example.taskmanager.taskmanager_service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create/{projectID}")
    public ResponseEntity<ApiResponse<Project>> createProject(@Valid @RequestBody ProjectDto projectDto)
            throws ExecutionException, InterruptedException {
        Project project = projectService.createProject(projectDto);
        return new ResponseEntity<>(
                ApiResponse.success("Project created successfully", project),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Project>> getProject(@PathVariable String id)
            throws ExecutionException, InterruptedException {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Project>>> getAllProjects()
            throws ExecutionException, InterruptedException {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(ApiResponse.success(projects));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<Project>> updateProject(
            @PathVariable String id,
            @Valid @RequestBody ProjectDto projectDto
    ) throws ExecutionException, InterruptedException {
        Project project = projectService.updateProject(id, projectDto);
        return ResponseEntity.ok(ApiResponse.success("Project updated successfully", project));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable String id)
            throws ExecutionException, InterruptedException {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted successfully", null));
    }
}