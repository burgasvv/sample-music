FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY /src ./src/
RUN mvn clean package

FROM openjdk:23 AS prod
LABEL authors="Burgas"

EXPOSE 8765
COPY --from=build target/identity-server-0.0.1-SNAPSHOT.jar identity-server.jar

ENTRYPOINT ["java", "-jar", "identity-server.jar"]