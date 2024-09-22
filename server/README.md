# ChessVger
version springboot de chessvger
## Purpose

Chess database

## Run

gradle bootRun -PRUN_FOLDER=run
gradle loadAllData -PRUN_FOLDER=run
gradle CreateDataBase -PRUN_FOLDER=run #perf
gradle LoadPlayer -PRUN_FOLDER=run

gradle loadAllData -PRUN_FOLDER=run;gradle LoadPlayer -PRUN_FOLDER=run;gradle CreateDataBase -PRUN_FOLDER=run
gradle loadAllData -PRUN_FOLDER=run;gradle CreateDataBase -PRUN_FOLDER=run

# TESTS

gradle test

# DOCUMENTATION

gradle.properties contient version=1.0.12
pgnAllData=C:\mesapplis/chessvger/twic1997
pgnLight=C:\mesapplis/chessvger/test
RUN_FOLDER=run

gradle loadAllData -PRUN_FOLDER=runTest
= le parametre de projet run_folder est passe a gradle
qui le repasse a l appli java loaddata

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

