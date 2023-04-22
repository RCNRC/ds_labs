#!/bin/bash

if [ ! -f spark-2.3.1-bin-hadoop2.7.tgz ]; then
    wget https://archive.apache.org/dist/spark/spark-2.3.1/spark-2.3.1-bin-hadoop2.7.tgz
    tar xvzf spark-2.3.1-bin-hadoop2.7.tgz
else
    echo "Spark already exists, skipping..."
fi

export SPARK_HOME=/spark-2.3.1-bin-hadoop2.7
export HADOOP_CONF_DIR=$HADOOP_PREFIX/etc/hadoop
export PATH=$PATH:/spark-2.3.1-bin-hadoop2.7/bin

export PATH=$PATH:/usr/local/hadoop/bin/

if [ ! -f apache-flume-1.11.0-bin.tar.gz ]; then
  wget --no-check-certificate https://dlcdn.apache.org/flume/1.11.0/apache-flume-1.11.0-bin.tar.gz
  tar xvzf apache-flume-1.11.0-bin.tar.gz
  rm -rf /apache-flume-1.11.0-bin/conf
  mkdir /apache-flume-1.11.0-bin/conf
  mv flume-hdfs-sink.conf /apache-flume-1.11.0-bin/conf
else
  echo "Flume already exists, skipping..."
fi

hadoop dfs -rm -r out_data
hadoop dfs -rm -r input_data
hadoop fs -mkdir hdfs://localhost:9000/user/root/input_data
rm -rf /input_data
mkdir /input_data

export JAVA_OPTS="-Xms100m -Xmx2000m -Dcom.sun.management.jmxremote"

/apache-flume-1.11.0-bin/bin/flume-ng agent -n LogAgent -c conf -f apache-flume-1.11.0-bin/conf/flume-hdfs-sink.conf


#hadoop fs -put /input_data hdfs://localhost:9000/user/root/input_data

