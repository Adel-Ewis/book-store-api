# Stage 1: Build the base application
FROM maven:3.8.4-openjdk-17 AS builder
# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Resolve project dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "target/bookstore-0.0.1-SNAPSHOT.jar"]