# generate global jar
gradle clean build allQueuesFatJar
docker build -t chessvger-queues . --no-cache