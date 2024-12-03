# generate global jar
gradle allQueuesFatJar
docker build -t chessvger-queues . --no-cache