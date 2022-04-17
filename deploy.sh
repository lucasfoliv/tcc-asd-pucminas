#!/usr/bin/env bash

if [[ -z $1 ]] || [[ $1 != "create" ]] && [[ $1 != "remove" ]] && [[ $1 != "recreate" ]]; then
  echo "$1 is not a valid option. It must be [CREATE, REMOVE or RECREATE]. Aborting!!!"
  exit 0
fi

pushd "$(pwd)/gateway" && ./deploy.sh "$1" && popd || exit
pushd "$(pwd)/identity" && ./deploy.sh "$1" && popd || exit
pushd "$(pwd)/middleware" && ./deploy.sh "$1" && popd || exit
pushd "$(pwd)/receiver" && ./deploy.sh "$1" && popd || exit
pushd "$(pwd)/registry" && ./deploy.sh "$1" && popd || exit
