# Use the official OpenJDK 17 image as a base
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
ADD target/DevOps_Project-1.0.jar /app/DevOps_Project.jar

# Expose the port on which the application will run
EXPOSE 8080

# Command to run the JAR file
CMD ["java", "-jar", "DevOps_Project.jar"]