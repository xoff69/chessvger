# docker build -t chessvger-webapp . --no-cache
# gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/. .
EXPOSE 8080
CMD ["java", "-jar","-Dspring.profiles.active=cluster", "web-1.0.0-SNAPSHOT.jar"]
