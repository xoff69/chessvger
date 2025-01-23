# docker build -t chessvger-backoffice . --no-cache
# gradle clean build allBackOfficeFatJar
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY build/libs/. .

ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://otel-collector:4317
ENV OTEL_SERVICE_NAME=java-service
ENV OTEL_TRACES_SAMPLER=always_on
ENV OTEL_METRICS_EXPORT_INTERVAL=10000

CMD ["java","-javaagent:/path/to/opentelemetry-javaagent.jar","-jar", "all-in-one-jar-1.0.0-SNAPSHOT.jar","cluster"]
