# Stage 1: Build phase
# Gumamit ng alpine version para mas mabilis i-download
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
# Siguraduhing tama ang folder name na 'demo' sa path mo
RUN mvn clean package -DskipTests -f demo/pom.xml

# Stage 2: Run phase
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Kunin ang JAR mula sa build stage
COPY --from=build /app/demo/target/*.jar app.jar

EXPOSE 8080

# PORT is provided by Railway automatically
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]