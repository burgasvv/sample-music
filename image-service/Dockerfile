FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

EXPOSE 9020
COPY target/image-service-0.0.1-SNAPSHOT.jar image-service.jar

ENTRYPOINT ["java", "-jar", "image-service.jar"]