FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

EXPOSE 9030
COPY target/sound-service-0.0.1-SNAPSHOT.jar sound-service.jar

ENTRYPOINT ["java", "-jar", "sound-service.jar"]