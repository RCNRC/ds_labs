#!/usr/bin/env bash
if [[ $# -eq 0 ]] ; then
    echo 'You should specify container name!'
    exit 1
fi

mvn package -f ../pom.xml

docker cp flume-hdfs-sink.conf $1:/
docker cp flume.sh $1:/
docker cp install.sh $1:/
docker cp elasticsearch.yml $1:/
docker cp work.conf $1:/
docker cp ../target/lab3-1.0-SNAPSHOT-jar-with-dependencies.jar $1:/
docker cp run.sh $1:/
