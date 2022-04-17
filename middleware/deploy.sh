#!/usr/bin/env bash

COMPOSE_FILE="$(pwd)/docker-compose.yml"
ENV_FILE="$(pwd)/.env"

create() {
  echo "====================================="
  echo "Building GISA Middleware Microservice"
  echo "====================================="

  ./mvnw clean package -Dquarkus.package.type=uber-jar -DskipTests

  echo "=================================================="
  echo "Building GISA Middleware Microservice Docker image"
  echo "=================================================="

  docker-compose --file "${COMPOSE_FILE}" --env-file "${ENV_FILE}" build --build-arg USER=gisa

  echo "==============================================="
  echo "Starting GISA Middleware Microservice container"
  echo "==============================================="

  docker-compose --file "${COMPOSE_FILE}" --env-file "${ENV_FILE}" up --detach
}

remove() {
  echo "==============================================="
  echo "Removing GISA Middleware Microservice container"
  echo "==============================================="

  docker-compose --file "${COMPOSE_FILE}" --env-file "${ENV_FILE}" down --volumes
}

case $1 in
"create")
  create
  ;;
"remove")
  remove
  ;;
"recreate")
  remove
  create
  ;;
*)
  echo "$1 is not a valid option. Aborting!!!"
  ;;
esac
