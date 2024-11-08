# Use a lightweight base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

ADD ../target/tp-foyer-5.0.0.jar /app/app.jar


# Expose the application's port
EXPOSE 8089

# Run the application
CMD ["java", "-jar", "app.jar"]