#!/usr/bin/env bash

JAR_FILE="$(pwd)/gateway.jar"

java -server -Dcom.sun.management.jmxremote -Djava.security.egd=file:/dev/./urandom --enable-preview -jar "${JAR_FILE}"
