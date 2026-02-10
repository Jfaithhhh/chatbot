FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# I-copy lahat (kasama ang demo folder)
COPY . .
# Pumasok sa demo folder para mag-build
RUN cd demo && mvn clean package -DskipTests

FROM eclipse-temurin-21-jre
WORKDIR /app
# Hanapin ang jar sa loob ng demo/target
COPY --from=build /app/demo/target/*.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=9091"]