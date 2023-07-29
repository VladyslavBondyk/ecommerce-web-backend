#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build
COPY . .
RUN mvn clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim

COPY --from=build /target/MASHHH-STORE-AWS.jar demo.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]