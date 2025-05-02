@echo off
REM This script starts the Firestore emulator for local development

REM Set environment variables for the application to use the emulator
SET FIRESTORE_EMULATOR_HOST=localhost:8080
SET GOOGLE_CLOUD_PROJECT=dogwood-thought-441315-n9

REM Start the Firestore emulator
echo Starting Firestore emulator on %FIRESTORE_EMULATOR_HOST%...
gcloud emulators firestore start --host-port=%FIRESTORE_EMULATOR_HOST%