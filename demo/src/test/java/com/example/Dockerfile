FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# I-copy ang buong folder contents
COPY . .

# Dahil ang Root ay nasa labas na, kailangan nating pumasok sa 'demo' folder para mag-build
RUN cd demo && mvn clean package -DskipTests

FROM eclipse-temurin-21-jre
WORKDIR /app
# Kunin ang jar mula sa demo/target
COPY --from=build /app/demo/target/*.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=9091"]