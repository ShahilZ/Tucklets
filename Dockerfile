FROM openjdk:11.0.7-slim-buster

RUN ls

RUN cd build
RUN ls
RUN cd build/libs
RUN ls
RUN cd../..

COPY app-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","app-0.0.1-SNAPSHOT.jar"]