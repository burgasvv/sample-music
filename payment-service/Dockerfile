FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

EXPOSE 9040
COPY target/payment-service-0.0.1-SNAPSHOT.jar payment-service.jar

ENTRYPOINT ["java", "-jar", "payment-service.jar"]