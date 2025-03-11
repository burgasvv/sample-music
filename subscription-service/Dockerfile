FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY /src ./src/
RUN mvn clean package

FROM openjdk:23 AS prod
LABEL authors="Burgas"

EXPOSE 9010
COPY --from=build target/subscription-service-0.0.1-SNAPSHOT.jar subscription-service.jar

ENTRYPOINT ["java", "-jar", "subscription-service.jar"]