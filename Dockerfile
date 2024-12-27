FROM eclipse-temurin:23-jdk-alpine

WORKDIR /app

COPY target/waltmann-api-1.0.0.jar waltmann-api-1.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "waltmann-api-1.0.0.jar"]