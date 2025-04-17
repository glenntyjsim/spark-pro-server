# syntax=docker/dockerfile:1

# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]