# Use Java 21 (matches your pom)
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose port (Render uses dynamic PORT)
EXPOSE 8080

# Start app
CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]