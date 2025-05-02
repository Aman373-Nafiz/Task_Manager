package com.example.taskmanager.taskmanager_service.config;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GoogleCredentialsConfig {

    @Bean
    @Primary
    public GoogleCredentials googleCredentials() throws IOException {

        String credentialsPath = "D:\\Java\\taskmanager_service\\GOOGLE_APPLICATION\\dogwood-thought-441315-n9-65ef9c3b3c42.json";
        File credentialsFile = new File(credentialsPath);
        if (!credentialsFile.exists()) {
            throw new IOException("Credentials file not found at: " + credentialsFile.getAbsolutePath());
        }

        System.out.println("Loading credentials from: " + credentialsFile.getAbsolutePath());

        return GoogleCredentials.fromStream(new FileInputStream(credentialsFile));
    }
}