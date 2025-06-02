#!/bin/bash
./gradlew clean shadowJar -x test
java -jar build/libs/PROP_SCRABBLE-all.jar
