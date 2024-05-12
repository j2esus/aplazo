FROM openjdk:17-jdk-slim
MAINTAINER aplazo.mx
COPY target/*.jar loan.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "loan.jar"]