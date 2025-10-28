# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Install Maven manually
RUN apt-get update && apt-get install -y maven

# Copy project files and build
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B -DskipTests package

# Run stage (using JDK 21 to run the app)
FROM eclipse-temurin:21-jre
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
