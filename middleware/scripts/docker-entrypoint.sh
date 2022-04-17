#!/usr/bin/env bash

JAR_FILE="$(pwd)/middleware.jar"

java -Djava.security.egd=file:/dev/./urandom --enable-preview -jar "${JAR_FILE}"
