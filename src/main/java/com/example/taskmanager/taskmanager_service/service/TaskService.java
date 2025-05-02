package com.example.taskmanager.taskmanager_service.service;
import com.example.taskmanager.taskmanager_service.dto.TaskDto;
import com.example.taskmanager.taskmanager_service.exception.ResourceNotFoundException;
import com.example.taskmanager.taskmanager_service.model.Project;
import com.example.taskmanager.taskmanager_service.model.Task;
import com.example.taskmanager.taskmanager_service.notification.TaskNotificationListener;
import com.example.taskmanager.taskmanager_service.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectService projectService, ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.eventPublisher = eventPublisher;
    }

    public Task createTask(TaskDto taskDto) throws ExecutionException, InterruptedException {

        Project project = projectService.getProjectById(taskDto.getProjectId());


        Task task = new Task();
        task.setProjectId(taskDto.getProjectId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus() != null ? taskDto.getStatus() : Task.status.TO_DO);
        Task savedTask = taskRepository.save(task);
        eventPublisher.publishEvent(new TaskNotificationListener.TaskCreatedEvent(
                this, savedTask, project.getName()));

        return savedTask;
    }

    public Task getTaskById(String id) throws ExecutionException, InterruptedException {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        return task;
    }

    public List<Task> getAllTasks() throws ExecutionException, InterruptedException {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByProjectId(String projectId) throws ExecutionException, InterruptedException {
        projectService.getProjectById(projectId);
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> getTasksByProjectIdAndStatus(String projectId, Task.status status)
            throws ExecutionException, InterruptedException {

        projectService.getProjectById(projectId);

        return taskRepository.findByProjectIdAndStatus(projectId, status);
    }

    public Task updateTask(String id, TaskDto taskDto) throws ExecutionException, InterruptedException {
        Task existingTask = getTaskById(id);


        if (!existingTask.getProjectId().equals(taskDto.getProjectId())) {
            projectService.getProjectById(taskDto.getProjectId());
        }

        existingTask.setProjectId(taskDto.getProjectId());
        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(String id) throws ExecutionException, InterruptedException {

        getTaskById(id);
        taskRepository.delete(id);
    }
}