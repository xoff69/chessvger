# docker build -t chessvger-webapp-admin . --no-cache
# gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/. .
EXPOSE 8082 14250 16686
CMD ["java", "-jar","-Dspring.profiles.active=cluster", "web_admin-1.0.0-SNAPSHOT.jar"]
