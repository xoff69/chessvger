#!/bin/bash

# frontend
echo "build frontend"
echo "TODO"
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
cd ..
# images
echo "build images"
docker build -t  chessvger-client  ./client/chessvger-client --no-cache
docker build -t chessvger-web ./server/web --no-cache
docker build -t chessvger-backoffice ./server/backoffice --no-cache