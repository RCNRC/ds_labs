#!/bin/sh

# Удаляет папки input и output из файловой системы hadoop, кладёт туда папку input.

hdfs dfs -rm -r input output
hdfs dfs -put input
