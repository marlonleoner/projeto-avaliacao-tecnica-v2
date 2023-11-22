FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-11-jdk maven -y

COPY . .

RUN mvn clean install

FROM openjdk:11-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /target/*.jar avaliacao-tecnica.jar

ENTRYPOINT [ "java", "-jar", "/app/avaliacao-tecnica.jar" ]
