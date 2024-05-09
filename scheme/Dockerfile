FROM openjdk:17-jdk-slim
MAINTAINER aplazo.mx
COPY target/*.jar scheme.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "scheme.jar"]