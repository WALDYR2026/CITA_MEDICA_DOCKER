# Etapa 1: compilacion y empaquetado Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
RUN mvn --batch-mode -DskipTests package

# Etapa 2: ejecucion sin Maven ni JDK de desarrollo
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/target/cita-medica-0.0.1-SNAPSHOT.jar app.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
