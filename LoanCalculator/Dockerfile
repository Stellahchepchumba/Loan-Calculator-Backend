# Use an official Java runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the application using Maven Wrapper
RUN ./mvnw package

# Specify the command to run the application
CMD ["java", "-jar", "target/LoanCalculator-0.0.1-SNAPSHOT.jar"]
