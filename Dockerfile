FROM openjdk:11.0.7-slim-buster

COPY build/libs/app-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

EXPOSE 8083

ENTRYPOINT ["java","-jar","app-0.0.1-SNAPSHOT.jar"]