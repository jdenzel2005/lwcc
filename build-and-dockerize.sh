#!/bin/bash
set -e

echo "Cleaning and building the JAR..."
./gradlew clean :src:lwcc-backend:bootJar

echo "Changing directory to lwcc-backend..."
cd src/lwcc-backend

echo "Building Docker image..."
docker build -t lwcc-backend:latest .

echo "Changing directory to lwcc-ui..."
cd ../lwcc-ui

echo "Building frontend Docker image"
docker build -t lwcc-ui:latest .

echo "Done."
