FROM openjdk:11.0.7-slim-buster

COPY app-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","app-0.0.1-SNAPSHOT.jar"]