#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim

COPY --from=build /target/MASHHH-STORE-AWS.jar demo.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]