#
# Build stage
#
FROM maven:3.8.6-openjdk-20 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:20-jdk-slim
COPY --from=build /target/MASHHH-STORE-AWS.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]