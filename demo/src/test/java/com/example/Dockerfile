FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Siguraduhing tama ang folder name (demo)
RUN mvn clean package -DskipTests -f demo/pom.xml

# PALITAN ITO: Gamitin ang specific na image path
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/demo/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]