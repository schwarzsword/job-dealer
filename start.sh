#! /bin/sh

# stop and remove containers created by 'docker-compose up'
docker-compose down

# clean artifact and rebuild maven application
cd ./backend/ && mvn clean install && cd ..

# build services
docker-compose build

# create and run containers for services
docker-compose up
