FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

EXPOSE 9010
COPY target/subscription-service-0.0.1-SNAPSHOT.jar subscription-service.jar

ENTRYPOINT ["java", "-jar", "subscription-service.jar"]