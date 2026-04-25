# Use Maven + Java 21 image
FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

# Copy everything
COPY . .

# Build app
RUN mvn clean package -DskipTests

# Run app
CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]