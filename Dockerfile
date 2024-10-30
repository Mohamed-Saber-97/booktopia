# Use Maven to build the package
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use an official JDK runtime as a parent image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Booktopia-0.0.1-SNAPSHOT.war Booktopia.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Booktopia.war"]
