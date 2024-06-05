#!/bin/bash

cd soup-shortener-url || exit
./gradlew clean
./gradlew bootJar
cd ..
docker-compose --env-file local.env stop
docker-compose --env-file local.env up --build -d
