# Stage 1: Build the Java application using Maven
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Stage 2: Create the final Docker image
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/devops-pipeline-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
