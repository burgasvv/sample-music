FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

EXPOSE 8080
COPY target/database-server-0.0.1-SNAPSHOT.jar database-server.jar

ENTRYPOINT ["java", "-jar", "database-server.jar"]