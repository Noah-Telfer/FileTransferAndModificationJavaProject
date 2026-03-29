# Stage 1: Build the application using JDK 17
FROM gradle:8.10-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# build -x test skips tests to speed up the container build during development
RUN ./gradlew build -x test --no-daemon

# Stage 2: Run the application using JRE 17 (Lighter/More Secure)
FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ingestion-service.jar
ENTRYPOINT ["java", "-jar", "/app/ingestion-service.jar"]