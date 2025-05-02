package com.example.taskmanager.taskmanager_service.controller;

import com.example.taskmanager.taskmanager_service.dto.TaskDto;
import com.example.taskmanager.taskmanager_service.dto.response.ApiResponse;
import com.example.taskmanager.taskmanager_service.model.Task;
import com.example.taskmanager.taskmanager_service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create/{projectID}")
    public ResponseEntity<ApiResponse<Task>> createTask(@Valid @RequestBody TaskDto taskDto)
            throws ExecutionException, InterruptedException {
        Task task = taskService.createTask(taskDto);
        return new ResponseEntity<>(
                ApiResponse.success("Task created successfully", task),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks() throws ExecutionException, InterruptedException {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getTask(@PathVariable String id)
            throws ExecutionException, InterruptedException {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponse.success(task));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<Task>>> getTasksByProject(@PathVariable String projectId)
            throws ExecutionException, InterruptedException {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }


    @GetMapping("/project/{projectId}/status/{status}")
    public ResponseEntity<ApiResponse<List<Task>>> getTasksByProjectAndStatus(
            @PathVariable String projectId,
            @PathVariable Task.status status
    ) throws ExecutionException, InterruptedException {
        List<Task> tasks = taskService.getTasksByProjectIdAndStatus(projectId, status);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(
            @PathVariable String id,
            @Valid @RequestBody TaskDto taskDto
    ) throws ExecutionException, InterruptedException {
        Task task = taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(ApiResponse.success("Task updated successfully", task));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable String id)
            throws ExecutionException, InterruptedException {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.success("Task deleted successfully", null));
    }
}