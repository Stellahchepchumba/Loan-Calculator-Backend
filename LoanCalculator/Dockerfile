# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set working directory inside the container
WORKDIR /build

# Copy Maven wrapper and pom.xml for dependency caching
COPY LoanCalculator/mvnw .
COPY LoanCalculator/.mvn .mvn
COPY LoanCalculator/pom.xml .

# Download dependencies (cached if pom.xml hasn't changed)
RUN ./mvnw dependency:go-offline -B

# Copy the application source code
COPY LoanCalculator/src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=builder /build/target/LoanCalculator-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
