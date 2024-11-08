# Use a lightweight base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY ./target/tp-foyer-5.0.0.jar app.jar

# Expose the application's port
EXPOSE 8089

# Run the application
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]