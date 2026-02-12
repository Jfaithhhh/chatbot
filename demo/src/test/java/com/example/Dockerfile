# Stage 1: Build phase
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Ginagamit ang -f demo/pom.xml dahil nasa root ang Dockerfile base sa settings mo
RUN mvn clean package -DskipTests -f demo/pom.xml

# Stage 2: Run phase
# DITO ANG FIX: Dapat ay may ':' sa pagitan ng image name at version
FROM eclipse-temurin:21-jre
WORKDIR /app
# Siguraduhing kinokopya ang jar mula sa tamang target folder
COPY --from=build /app/demo/target/*.jar app.jar

EXPOSE 8080

# Ang PORT ay variable na galing mismo sa Railway
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]