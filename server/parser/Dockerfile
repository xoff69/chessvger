# docker build -t chessvger-parser . --no-cache
# gradle clean build allParserFatJar
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY build/libs/. .

CMD ["java", "-jar", "all-in-one-jar-1.0.0-SNAPSHOT.jar","cluster"]
