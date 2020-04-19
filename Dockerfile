FROM openjdk:11.0.7-slim-buster

COPY build/libs/app-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch app-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","app-0.0.1-SNAPSHOT.jar"]