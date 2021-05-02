#!/bin/bash

set JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64/bin/"

javac src/musichub/business/*.java src/musichub/util/*.java src/musichub/main/*.java -d bin
