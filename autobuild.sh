#!/bin/bash

git pull origin new-pow
./gradlew build

nohub java -jar ~/be-java-cafe/build/libs/cafe-0.0.1-SNAPSHOT.jar &
