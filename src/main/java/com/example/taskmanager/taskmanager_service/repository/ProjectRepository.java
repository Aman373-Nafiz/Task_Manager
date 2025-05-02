package com.example.taskmanager.taskmanager_service.repository;
import com.example.taskmanager.taskmanager_service.model.Project;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class ProjectRepository {

    private final CollectionReference projectsCollection;

    @Autowired
    public ProjectRepository(Firestore firestore) {
        this.projectsCollection = firestore.collection("projects");
    }

    public static String shortId() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public Project save(Project project) throws ExecutionException, InterruptedException {
        Timestamp now = Timestamp.now();

        if (project.getId() == null || project.getId().isEmpty()) {

            project.setCreatedAt(now);
            project.setUpdatedAt(now);

            DocumentReference docRef = projectsCollection.document(shortId());
            project.setId(docRef.getId());

            ApiFuture<WriteResult> result = docRef.set(project);
            result.get();
        } else {

            project.setUpdatedAt(now);

            DocumentReference docRef = projectsCollection.document(String.valueOf(project.getId()));
            ApiFuture<WriteResult> result = docRef.set(project);
            result.get();
        }

        return project;
    }

    public Project findById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = projectsCollection.document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(Project.class);
        } else {
            return null;
        }
    }

    public List<Project> findAll() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = projectsCollection.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Project> projects = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            projects.add(document.toObject(Project.class));
        }

        return projects;
    }

    public void delete(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = projectsCollection.document(id);
        ApiFuture<WriteResult> result = docRef.delete();
        result.get();
    }
}