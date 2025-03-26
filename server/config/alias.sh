echo "alias buildbo='pushd /c/home/developpement/chessvger/server/backoffice && gradle clean build allBackOfficeFatJar && popd'" >> ~/.bashrc
echo "alias buildw='pushd /c/home/developpement/chessvger/server/web && gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test && popd'" >> ~/.bashrc


source ~/.bashrc