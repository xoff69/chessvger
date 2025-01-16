# docker build -t chessvger-backoffice . --no-cache
# gradle clean build allBackOfficeFatJar
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY build/libs/. .


CMD ["java","-jar", "all-in-one-jar-1.0.0-SNAPSHOT.jar","cluster"]
