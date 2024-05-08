FROM openjdk:17-jdk-slim
MAINTAINER aplazo.mx
COPY target/*.jar credit-line-app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "credit-line-app.jar"]