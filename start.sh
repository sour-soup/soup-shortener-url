#!/bin/bash

cd soup-shortener-url || exit
./gradlew bootJar
cd ..
docker-compose --env-file local.env up
