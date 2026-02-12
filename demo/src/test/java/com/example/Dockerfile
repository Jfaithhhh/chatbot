FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Siguraduhin na ang path ay 'demo/pom.xml' dahil nasa root na ang Dockerfile
RUN mvn clean package -DskipTests -f demo/pom.xml

FROM eclipse-temurin-21-jre
WORKDIR /app
# Kunin ang jar mula sa demo/target
COPY --from=build /app/demo/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]