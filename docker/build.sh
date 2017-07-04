#! /bin/bash

cd ..
docker build --no-cache -t planetek/cmems_manager_od:$1 --build-arg branch=$1 .