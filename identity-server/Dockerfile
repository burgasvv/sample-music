FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

EXPOSE 8765
COPY target/identity-server-0.0.1-SNAPSHOT.jar identity-server.jar

ENTRYPOINT ["java", "-jar", "identity-server.jar"]