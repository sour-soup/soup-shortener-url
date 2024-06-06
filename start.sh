#!/bin/bash

cd backend || exit
./gradlew clean
./gradlew bootJar
cd ..
docker-compose --env-file .env stop
docker-compose --env-file .env up --build -d
