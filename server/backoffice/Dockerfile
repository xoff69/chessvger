# docker build -t chessvger-backoffice . --no-cache
# gradle clean build allBackOfficeFatJar
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY build/libs/. .

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.28.0/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

CMD ["java", "-Dotel.exporter.jaeger.endpoint=http://localhost:14250 ","-javaagent:/opentelemetry-javaagent.jar","-jar", "all-in-one-jar-1.0.0-SNAPSHOT.jar","cluster"]
