#!/bin/bash

# frontend
echo "build frontend"
cd client
cd chessvger-client
npm install
npm update
npm run build
cd ..
cd ..
#
echo "build core"
cd server
cd core
gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
cd ..
# backoffice
echo "build backoffice"
cd backoffice
gradle clean build allBackOfficeFatJar
cd ..
# web
echo "build web"
cd web
gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
cd ..
# web
echo "build web admin"
cd web_admin
gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
cd ..
cd ..
# images
echo "build images"
docker build -t  chessvger-client  ./client/chessvger-client --no-cache
docker build -t  chessvger-client-admin  ./client/chessvger-admin --no-cache
docker build -t chessvger-serverapp ./server/web --no-cache
docker build -t chessvger-serverapp-admin ./server/web_admin --no-cache
docker build -t chessvger-backoffice ./server/backoffice --no-cache