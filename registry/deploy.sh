#!/usr/bin/env bash

COMPOSE_FILE="$(pwd)/docker-compose.yml"
ENV_FILE="$(pwd)/.env"

create() {
  echo "========================================"
  echo "Building GISA Data Registry Microservice"
  echo "========================================"

  ./mvnw clean package -DskipTests

  echo "====================================================="
  echo "Building GISA Data Registry Microservice Docker image"
  echo "====================================================="

  docker-compose --file "${COMPOSE_FILE}" --env-file "${ENV_FILE}" build --build-arg USER=gisa

  echo "==================================================="
  echo "Starting GISA Data Registry Microservice containers"
  echo "==================================================="

  docker-compose --file "${COMPOSE_FILE}" --env-file "${ENV_FILE}" up --detach
}

remove() {
  echo "==================================================="
  echo "Removing GISA Data Registry Microservice containers"
  echo "==================================================="

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
