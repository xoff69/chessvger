# Utiliser une image officielle avec Java
FROM openjdk:21-jdk-slim

# Installer Git et Gradle
RUN apt-get update && apt-get install -y git wget unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Installer Gradle
ENV GRADLE_VERSION=8.3
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt && \
    rm gradle-${GRADLE_VERSION}-bin.zip
ENV PATH="/opt/gradle-${GRADLE_VERSION}/bin:${PATH}"

# Définir le répertoire de travail



RUN git clone https://github.com/xoff69/chessvger.git
WORKDIR /chessvger/server/
# Construire le projet avec Gradle
RUN gradle build --no-daemon
WORKDIR /app
# Exposer le port par défaut de l'application Spring Boot
EXPOSE 8080

# Démarrer l'application Spring Boot
CMD ["java", "-jar", "build/libs/chessvger-1.0.0-SNAPSHOT.jar"]
