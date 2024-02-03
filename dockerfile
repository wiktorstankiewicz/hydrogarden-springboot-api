FROM maven:3.8.4-openjdk-17

# Set the working directory
WORKDIR /app

# Copy the entire project to the container


COPY ./pom.xml ./pom.xml
COPY ./mvnw ./mvnw
COPY ./mvnw.cmd ./mvnw.cmd
CMD ["mvn", "clean", "install"]


COPY ./src ./src

# Run the Spring Boot application using Maven
CMD ["mvn", "spring-boot:run"]
