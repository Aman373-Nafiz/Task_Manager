package com.example.taskmanager.taskmanager_service.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirestoreConfig {

    @Value("${spring.cloud.gcp.project-id:dogwood-thought-441315-n9}")
    private String projectId;

    @Bean
    public Firestore firestore() throws IOException {

        String credentialsPath = "D:\\Java\\taskmanager_service\\GOOGLE_APPLICATION\\dogwood-thought-441315-n9-65ef9c3b3c42.json";
        File credentialsFile = new File(credentialsPath);
        if (!credentialsFile.exists()) {
            throw new IOException("Credentials file not found at: " + credentialsFile.getAbsolutePath());
        }

        FileInputStream serviceAccount = new FileInputStream(credentialsFile);

        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId("dogwood-thought-441315-n9")
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return firestoreOptions.getService();
    }
}