FROM openjdk:11.0.7-slim-buster

COPY application-jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","application.jar"]