# Use the official OpenJDK 17 image as a base
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY ./target/tp-foyer-1.0.0.jar app.jar

# Expose the port on which the application will run
EXPOSE 8089

# Command to run the JAR file
CMD ["java", "-jar", "tp-foyer.jar"]
