# docker build -t chessvger-backoffice . --no-cache
# gradle clean build allBackOfficeFatJar
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY build/libs/. .
EXPOSE  14250 16686
CMD ["java", "-jar", "all-in-one-jar-1.0.0-SNAPSHOT.jar","cluster"]
