# ChessVger
version springboot de chessvger
## Purpose

Chess database

## Run

gradle bootRun -PRUN_FOLDER=run


# TESTS

gradle test

# DOCUMENTATION

gradle.properties contient version=1.0.12



gradle bootRun --args='RUN_FOLDER=runTest'

ne ps utiliser gradlew

gradle test -PRUN_FOLDER=runTest

# Verification tools

## JACOCO

- gradle test jacocoTestReport
- gradle combineJaCoCoReports

## CHECKSTYLE

- gradle checkstyleMain

## PMD

- gradle pmdMain pmd

## SPOTBUG

- gradle spotBugsMain

##DB
docker run -itd -e POSTGRES_USER=chessvger -e POSTGRES_PASSWORD=chessvger -p 5432:5432 -v ./data:/var/lib/postgresql/data --name postgresql postgres