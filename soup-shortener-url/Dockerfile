FROM gradle:8.8.0-jdk17-alpine AS build
WORKDIR /app
COPY ./soup-shortener-url/ .
RUN gradle bootJar

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/soup-url-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]