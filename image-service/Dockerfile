FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY /src ./src/
RUN mvn clean package

FROM openjdk:23 AS prod
LABEL authors="Burgas"

EXPOSE 9020
COPY --from=build target/image-service-0.0.1-SNAPSHOT.jar image-service.jar

ENTRYPOINT ["java", "-jar", "image-service.jar"]