package com.example.taskmanager.taskmanager_service.notification;

import com.example.taskmanager.taskmanager_service.model.Task;
import lombok.Getter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationEvent;

import java.util.logging.Logger;

@Component
public class TaskNotificationListener {
    private static final Logger logger = Logger.getLogger(TaskNotificationListener.class.getName());
    @Getter
    public static class TaskCreatedEvent extends ApplicationEvent {
        private final Task task;
        private final String projectName;

        public TaskCreatedEvent(Object source, Task task, String projectName) {
            super(source);
            this.task = task;
            this.projectName = projectName;
        }

    }


    @EventListener
    @Async
    public void handleTaskCreatedEvent(TaskCreatedEvent event) {
        Task task = event.getTask();
        String projectName = event.getProjectName();
        logger.info(String.format("New task created: \"%s\" in project \"%s\"",
                task.getTitle(), projectName));

        System.out.println("✅ NOTIFICATION: New task created: \"" + task.getTitle() +
                "\" in project \"" + projectName + "\"");
    }
}