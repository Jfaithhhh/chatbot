# Gamit tayo ng stable na Maven/Java version
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Dahil nasa loob na tayo ng 'demo' folder, i-copy lahat ng laman nito
COPY . .

# I-build ang jar file
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin-21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=9091"]