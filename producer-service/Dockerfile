FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

EXPOSE 9000
COPY target/producer-service-0.0.1-SNAPSHOT.jar producer-service.jar

ENTRYPOINT ["java", "-jar", "producer-service.jar"]