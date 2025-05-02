# TaskManager Microservice
A Cloud-based task management service with Google Cloud integration for efficient task scheduling, tracking, and management.A Simple Task Management Platform to help small teams track tasks within projects, built using Spring Boot, Firestore, Cloud Run, and Cloud Functions.

## Features

- Create, update, delete, and view projects
- Create, update, delete, and view tasks within projects
- Filter tasks by status (TO_DO, IN_PROGRESS, DONE)
- Automatic notifications when new tasks are created triggered by database

## Architecture

- **Backend**: Java Spring Boot REST API
- **Database**: Google Cloud Firestore


## Setup Instructions


# Prerequisites
- Java JDK 11+
- Maven 3.6+
- Git
- Google Cloud Platform account


# Clone the repository
git clone https://github.com/Aman373-Nafiz/Task_Manager.git
cd taskmanager_service

# Build the project
- mvn clean install
- Setup Instructions
- Configuration
- Google Cloud Authentication
This application requires Google Cloud credentials to function properly. NEVER commit service account credentials to version control.
# Create a directory for credentials (ignored by git)
mkdir -p GOOGLE_APPLICATION
# Required Setup:

- Create a service account in Google Cloud Console
- Download the service account key (JSON format)
- Place the JSON key file in the GOOGLE_APPLICATION/ directory
- Set the environment variable:

# Linux/MacOS
export GOOGLE_APPLICATION_CREDENTIALS="$PWD/GOOGLE_APPLICATION/your-credentials-file.json"

# Windows (CMD)
set GOOGLE_APPLICATION_CREDENTIALS=D:\Java\taskmanager_service\GOOGLE_APPLICATION\your-credentials-file.json

# Windows (PowerShell)
$env:GOOGLE_APPLICATION_CREDENTIALS="D:\Java\taskmanager_service\GOOGLE_APPLICATION\your-credentials-file.json"
Git Setup for Credentials Security
Add the following to your .gitignore file:
# Ignore credential files
GOOGLE_APPLICATION/*.json
If you accidentally added credentials to Git tracking:
# Remove credentials from Git tracking without deleting the file
git rm --cached GOOGLE_APPLICATION/*.json

# Commit the change
git commit -m "Remove sensitive credentials and update gitignore"
Running the Application
# Start the service
mvn spring-boot:run
API Documentation
Access the Swagger UI at http://localhost:8080/swagger-ui.html after starting the application.

### Prerequisites

- Java 11+
- Maven
- Docker
- Google Cloud SDK
- Google Cloud Project with Firestore enabled

### Local Development

1. Clone the repository
```bash
git clone https://github.com/yourusername/taskmanager-service.git
cd taskmanager-service

taskmanager-service/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/taskmanager/taskmanager_service/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── exception/
│       │       ├── model/
│       │       ├── repository/
│       │       ├── service/
│       │       └── TaskManagerServiceApplication.java
│       └── resources/
│           └── application.properties
├── GOOGLE_APPLICATION/
│   └── dogwood-thought-441315-n9-65ef9c3b3c42.json
├── function/
│   └── src/main/java/com/example/taskmanager/function/
│       └── TaskNotificationFunction.java
├── Dockerfile
├── pom.xml
├── README.md
└── sample-data/
    ├── projects.json
    └── tasks.json



# Firestore Data Model

The application uses the following Firestore collections:

## Projects Collection

```json
{
  "id": "abc123",
  "name": "Marketing Website",
  "description": "Website redesign project for Q3",
  "createdAt": "2023-09-01T10:00:00Z",
  "updatedAt": "2023-09-01T10:00:00Z"
}
```

## Tasks Collection

```json
{
  "id": "xyz789",
  "projectId": "abc123",
  "title": "Design UI Mockups",
  "description": "Create mockups for the homepage and product pages",
  "status": "IN_PROGRESS",
  "createdAt": "2023-09-02T14:30:00Z",
  "updatedAt": "2023-09-03T09:15:00Z"
}
```

# Technologies Used

- **Java 17**
- **Spring Boot**: Core framework for building the REST API
- **Google Cloud Firestore**: NoSQL document database for data storage
- **Google Cloud Run**: Serverless platform for hosting the API
- **Google Cloud Functions**: For handling Firestore triggers (notifications)
- **Lombok**: Reduces boilerplate code

# API Endpoints

## Project Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/projects/create/{projectID}` | Create a new project |
| GET | `/api/projects/get/{id}` | Get project details by ID |
| GET | `/api/projects/list` | List all projects |
| PUT | `/api/projects/update/{id}` | Update a project |
| DELETE | `/api/projects/delete/{id}` | Delete a project |

## Task Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks/create/{projectID}` | Create a new task in a project |
| GET | `/api/tasks/list` | List all tasks |
| GET | `/api/tasks/{id}` | Get task details by ID |
| GET | `/api/tasks/project/{projectId}` | List all tasks in a project |
| GET | `/api/tasks/project/{projectId}/status/{status}` | Filter tasks by project and status |
| PUT | `/api/tasks/update/{id}` | Update a task |
| DELETE | `/api/tasks/delete/{id}` | Delete a task |

# Setup Instructions

## Prerequisites

1. Java 17 or later
2. Maven
3. Google Cloud account
4. Google Cloud CLI (`gcloud`)
5. Firestore setup in your Google Cloud project

## Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/task-manager-microservice.git
   cd task-manager-microservice
   ```

2. Configure Google Cloud credentials:
   ```bash
   # Set default project
   gcloud config set project YOUR_GCP_PROJECT_ID
   
   # Authenticate with Google Cloud
   gcloud auth application-default login
   ```

3. Build the application:
   ```bash
   mvn clean package
   ```

4. Run the application locally:
   ```bash
   mvn spring-boot:run
   ```

   The API will be available at `http://localhost:8080`



# Testing the API

## Sample Project Requests

### Create a Project
```bash
curl -X POST https://YOUR_CLOUD_RUN_URL/api/projects/create/project1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Marketing Website",
    "description": "Website redesign project for Q3"
  }'
```

### Get a Project
```bash
curl -X GET https://YOUR_CLOUD_RUN_URL/api/projects/get/project1
```

## Sample Task Requests

### Create a Task
```bash
curl -X POST https://YOUR_CLOUD_RUN_URL/api/tasks/create/task1 \
  -H "Content-Type: application/json" \
  -d '{
    "projectId": "project1",
    "title": "Design UI Mockups",
    "description": "Create mockups for the homepage and product pages",
    "status": "TO_DO"
  }'
```

### Get Tasks by Project
```bash
curl -X GET https://YOUR_CLOUD_RUN_URL/api/tasks/project/project1
```

### Get Tasks by Project and Status
```bash
curl -X GET https://YOUR_CLOUD_RUN_URL/api/tasks/project/project1/status/IN_PROGRESS
```

# Bonus Features Implemented

- **Filtering by Status**: Tasks can be filtered by their status (TO_DO, IN_PROGRESS, DONE)
- **Error Handling**: Comprehensive error handling with appropriate HTTP status codes
- **Input Validation**: Validation for all input data
- **Notification System**: Automatic logging of new task creation events

# Cloud Function for Task Notifications

A Cloud Function has been implemented to listen for new task creation events in the Firestore Tasks collection. The function logs a message like:

```
New task created: "Design UI Mockups" in project "Marketing Website"
```

This simulates a notification to team members. In a production environment, this could be extended to send emails, Slack messages, or other types of notifications.

# Sample API Responses

## Successful Response
```json
{
  "success": true,
  "message": "Project created successfully",
  "data": {
    "id": "abc123",
    "name": "Marketing Website",
    "description": "Website redesign project for Q3",
    "createdAt": "2023-09-01T10:00:00Z",
    "updatedAt": "2023-09-01T10:00:00Z"
  }
}
```

## Error Response
```json
{
  "timestamp": "2023-09-01T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Project not found with id: xyz",
  "path": "/api/projects/get/xyz"
}
```

# Notes

- The application uses UUID for generating IDs for both Projects and Tasks.
- The TaskNotificationListener simulates notifications via console logging. In a production environment, this could be extended to send emails or other types of notifications.

## ScreenShot
# Notification
![notifications](https://github.com/user-attachments/assets/9fde0fc0-bc14-456e-916c-63e3b405634d)

# Get Function of Project and Task (One Example each)
![get project list](https://github.com/user-attachments/assets/b91ea9d5-c7de-48d9-809b-f7066806c00e)

![p5](https://github.com/user-attachments/assets/e87c4bf3-8a72-4316-899a-f4d13bf51f8b)

# Post Function of Project and Task (One Example each )
![Screenshot (103)](https://github.com/user-attachments/assets/a2949d68-dab4-498d-885b-eb4762dae7b7)
![Screenshot (99)](https://github.com/user-attachments/assets/62232614-8077-4f02-902c-4c85110a131c)
# Update Function of Project
![Screenshot (104)](https://github.com/user-attachments/assets/c29375d2-329f-47a9-9b07-cbc37e30ab45)

# CloudFirestore Structure stored data
![p2](https://github.com/user-attachments/assets/4178c1d3-1362-456b-a4ff-65c49bc4365d)
![Screenshot (98)](https://github.com/user-attachments/assets/20e3a8b3-e2dd-4131-8454-690477fc43f5)
