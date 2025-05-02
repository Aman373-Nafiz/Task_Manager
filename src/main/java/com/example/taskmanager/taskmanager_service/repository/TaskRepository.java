package com.example.taskmanager.taskmanager_service.repository;

import com.example.taskmanager.taskmanager_service.model.Task;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
public class TaskRepository {

    private final Firestore firestore;
    private final CollectionReference tasksCollection;

    @Autowired
    public TaskRepository(Firestore firestore) {
        this.firestore = firestore;
        this.tasksCollection = firestore.collection("tasks");
    }
    public static String shortId() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
    public Task save(Task task) throws ExecutionException, InterruptedException {
        Timestamp now = Timestamp.now();

        if (task.getId() == null || task.getId().isEmpty()) {

            task.setCreatedAt(now);
            task.setUpdatedAt(now);
            DocumentReference docRef = tasksCollection.document(shortId());
            task.setId(docRef.getId());

            ApiFuture<WriteResult> result = docRef.set(task);
            result.get();
        } else {

            task.setUpdatedAt(now);

            DocumentReference docRef = tasksCollection.document(task.getId());
            ApiFuture<WriteResult> result = docRef.set(task);
            result.get();
        }

        return task;
    }

    public Task findById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = tasksCollection.document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(Task.class);
        } else {
            return null;
        }
    }

    public List<Task> findByProjectId(String projectId) throws ExecutionException, InterruptedException {
        Query query = tasksCollection.whereEqualTo("projectId", projectId);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Task> tasks = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            tasks.add(document.toObject(Task.class));
        }

        return tasks;
    }


    public List<Task> findByProjectIdAndStatus(String projectId, Task.status status)
            throws ExecutionException, InterruptedException {
        Query query = tasksCollection
                .whereEqualTo("projectId", projectId)
                .whereEqualTo("status", status);

        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Task> tasks = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            tasks.add(document.toObject(Task.class));
        }

        return tasks;
    }

    public void delete(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = tasksCollection.document(id);
        ApiFuture<WriteResult> result = docRef.delete();
        result.get();
    }

    public List<Task> findAll() throws ExecutionException, InterruptedException{
        ApiFuture<QuerySnapshot> future = tasksCollection.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Task> tasks = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            tasks.add(document.toObject(Task.class));
        }

        return tasks;
    }
}