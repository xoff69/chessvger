# generate global jar
gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test
docker build -t chessvger-webapp . --no-cache