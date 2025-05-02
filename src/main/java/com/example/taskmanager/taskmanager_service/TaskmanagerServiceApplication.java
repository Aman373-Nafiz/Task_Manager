package com.example.taskmanager.taskmanager_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TaskmanagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerServiceApplication.class, args);
	}

}
