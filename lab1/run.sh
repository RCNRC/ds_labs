#!/bin/sh

# Удаляет папки input и output из файловой системы hadoop, кладёт туда папку input, после чего запускает работу на псевдо распределённом кластере.

hdfs dfs -rm -r input output
hdfs dfs -put input

yarn jar ./target/lab1-1.0-SNAPSHOT-jar-with-dependencies.jar input output