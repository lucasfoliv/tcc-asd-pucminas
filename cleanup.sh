#!/usr/bin/env bash

DANGLING_IMAGES=$(docker images --quiet --filter="dangling=true")

if [[ -n ${DANGLING_IMAGES} ]]; then
  echo "========================================"
  echo "Removing existing Docker dangling images"
  echo "========================================"

  for element in ${DANGLING_IMAGES}; do
    echo "Deleting dangling image => ${element}"
    docker rmi --force "${element}"
  done
fi
