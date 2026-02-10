# Build stage
FROM maven:3-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin-21-jre
COPY --from=build /target/*.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=9091"]