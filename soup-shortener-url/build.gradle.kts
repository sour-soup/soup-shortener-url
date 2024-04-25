plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.32")
    implementation(group = "com.h2database", name = "h2", version = "2.2.224")
    implementation(group = "org.springframework", name = "spring-context", version = "6.1.3")
}

tasks.test {
    useJUnitPlatform()
}