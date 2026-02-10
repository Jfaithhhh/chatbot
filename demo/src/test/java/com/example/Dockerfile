FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN cd demo && mvn clean package -DskipTests

FROM eclipse-temurin-21-jre
WORKDIR /app
COPY --from=build /app/demo/target/*.jar app.jar

# Tanggalin natin ang EXPOSE 9091 at palitan ang ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:8080}"]