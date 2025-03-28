echo "alias buildbo='pushd /c/home/developpement/chessvger/server/backoffice && gradle clean build allBackOfficeFatJar && popd'" >> ~/.bashrc
echo "alias buildw='pushd /c/home/developpement/chessvger/server/web && gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test && popd'" >> ~/.bashrc
echo "alias buildc='pushd /c/home/developpement/chessvger/server/core && gradle build --no-daemon -x spotbugsMain -x spotbugsTest -x test && popd'" >> ~/.bashrc

export PROMPT_COMMAND="history -a; history -c; history -r; $PROMPT_COMMAND"

source ~/.bashrc