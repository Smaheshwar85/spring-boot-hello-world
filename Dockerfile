# Use official openjdk image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file to the container
COPY target/spring-boot-hello-world-1.0-SNAPSHOT.jar /app/spring-boot-hello-world-1.0-SNAPSHOT.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "my-spring-boot-app.jar"]

