# Use an official Java runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy only necessary Maven files first (improves caching)
COPY LoanCalculator/mvnw .
COPY LoanCalculator/.mvn .mvn

# Copy the rest of the project
COPY LoanCalculator/ .

# Ensure the Maven wrapper has executable permissions
RUN chmod +x ./mvnw

# Build the application using Maven Wrapper
RUN ./mvnw package

# Specify the command to run the application
CMD ["java", "-jar", "target/LoanCalculator-0.0.1-SNAPSHOT.jar"]
