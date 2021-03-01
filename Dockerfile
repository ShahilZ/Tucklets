FROM adoptopenjdk/openjdk11:jdk-11.0.7_10-alpine

# Docker Context is the build directory.
COPY libs/app-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

EXPOSE 8443

ENTRYPOINT ["java","-jar","app-0.0.1-SNAPSHOT.jar"]