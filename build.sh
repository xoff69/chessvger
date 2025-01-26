#!/bin/bash

# Fonction pour afficher l'aide
usage() {
    echo "Usage: $0 [all|frontend|frontend_admin|core|backoffice|web|web_admin|images]"
    exit 1
}

# Vérifier si un paramètre est passé
if [ $# -eq 0 ]; then
    usage
fi

# Fonctions de build
build_frontend() {
    echo "Building frontend..."
    cd client/chessvger-client
    npm install
    npm update
    npm run build
    cd ../..
}
build_frontend_admin() {
    echo "Building frontend admin..."
    cd client/chessvger-admin
    npm install
    npm update
    npm run build
    cd ../..
}
build_core() {
    echo "Building core..."
    cd server/core
    gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
    cd ../..
}

build_backoffice() {
    echo "Building backoffice..."
    cd server/backoffice
    gradle clean build allBackOfficeFatJar
    cd ../..
}

build_web() {
    echo "Building web..."
    cd server/web
    gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
    cd ../..
}

build_web_admin() {
    echo "Building web admin..."
    cd server/web_admin
    gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
    cd ../..
}

build_images() {
    echo "Building Docker images..."
    docker build -t chessvger-client ./client/chessvger-client --no-cache
    docker build -t chessvger-client-admin ./client/chessvger-admin --no-cache
    docker build -t chessvger-serverapp ./server/web --no-cache
    docker build -t chessvger-serverapp-admin ./server/web_admin --no-cache
    docker build -t chessvger-backoffice ./server/backoffice --no-cache
    docker compose up
}

# Traitement des arguments
case "$1" in
    all)
        build_frontend
        build_frontend_admin
        build_core
        build_backoffice
        build_web
        build_web_admin
        build_images
        ;;
    frontend) build_frontend ;;
    frontend_admin) build_frontend_admin ;;
    core) build_core ;;
    backoffice) build_backoffice ;;
    web) build_web ;;
    web_admin) build_web_admin ;;
    images) build_images ;;
    *) usage ;;  # Si un argument non valide est donné
esac
