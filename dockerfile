FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY target/*.jar app.jar

# Path: dockerfile
EXPOSE 30437

ENTRYPOINT ["java","-jar","/app/app.jar"]