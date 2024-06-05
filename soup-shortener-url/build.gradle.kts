plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.projectlombok:lombok")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.liquibase:liquibase-core")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.data:spring-data-redis")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("io.lettuce:lettuce-core:6.3.2.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    implementation("net.javacrumbs.shedlock:shedlock-spring:5.13.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:5.13.0")

    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
