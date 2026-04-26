# Use Maven + Java 21 image
FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

# Copy everything
COPY . .

# Build app
RUN mvn clean package -DskipTests

# Run app
CMD ["java", "-Djdk.tls.client.protocols=TLSv1.2,TLSv1.3", "-Djdk.tls.acknowledgeCloseNotify=true", "-jar", "target/quarkus-app/quarkus-run.jar"]
