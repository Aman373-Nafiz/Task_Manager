#!/bin/bash

# Build the Java application
mvn clean package -DskipTests

# Build the Docker image
docker build -t gcr.io/dogwood-thought-441315-n9-65ef9c3b3c42/taskmanager-api -f deployment/Dockerfile .

# Push to Google Container Registry
gcloud auth configure-docker
docker push gcr.io/dogwood-thought-441315-n9-65ef9c3b3c42/taskmanager-api

# Deploy to Cloud Run
gcloud run deploy taskmanager-api \
  --image gcr.io/dogwood-thought-441315-n9-65ef9c3b3c42/taskmanager-api \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated \
  --memory 512Mi \
  --cpu 1 \
  --max-instances 1