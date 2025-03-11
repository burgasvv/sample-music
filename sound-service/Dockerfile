FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY /src ./src/
RUN mvn clean package

FROM openjdk:23 AS prod
LABEL authors="Burgas"

EXPOSE 9030
COPY --from=build target/sound-service-0.0.1-SNAPSHOT.jar sound-service.jar

ENTRYPOINT ["java", "-jar", "sound-service.jar"]