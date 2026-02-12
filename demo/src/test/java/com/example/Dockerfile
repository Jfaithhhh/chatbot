FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Siguraduhing kinokopya ang buong folder content
COPY . .

# Dahil ang Dockerfile ay nasa labas na ng 'demo', kailangan ang -f demo/pom.xml
RUN mvn clean package -DskipTests -f demo/pom.xml

FROM eclipse-temurin-21-jre
WORKDIR /app

# Kunin ang JAR mula sa build stage
COPY --from=build /app/demo/target/*.jar app.jar

EXPOSE 8080

# Gamitin ang variable na PORT na binibigay ng Railway
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]