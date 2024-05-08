FROM openjdk:17-jdk-slim
MAINTAINER aplazo.mx
COPY target/*.jar customer-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "customer-app.jar"]