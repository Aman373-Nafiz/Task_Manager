package com.example.taskmanager.taskmanager_service.service;
import com.example.taskmanager.taskmanager_service.dto.ProjectDto;
import com.example.taskmanager.taskmanager_service.exception.ResourceNotFoundException;
import com.example.taskmanager.taskmanager_service.model.Project;
import com.example.taskmanager.taskmanager_service.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(ProjectDto projectDto) throws ExecutionException, InterruptedException {
        Project project = new Project();
        project.setName(projectDto.getName());

        project.setDescription(projectDto.getDescription());

        return projectRepository.save(project);
    }

    public Project getProjectById(String id) throws ExecutionException, InterruptedException {
        Project project = projectRepository.findById(id);
        if (project == null) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        return project;
    }

    public List<Project> getAllProjects() throws ExecutionException, InterruptedException {
        return projectRepository.findAll();
    }

    public Project updateProject(String id, ProjectDto projectDto) throws ExecutionException, InterruptedException {
        Project existingProject = getProjectById(id);
        existingProject.setName(projectDto.getName());
        existingProject.setDescription(projectDto.getDescription());

        return projectRepository.save(existingProject);
    }

    public void deleteProject(String id) throws ExecutionException, InterruptedException {
        getProjectById(id);
        projectRepository.delete(id);
    }
}