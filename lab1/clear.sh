#!/bin/sh

# Удаляет локальную папку input и папки input и output из файловой системы hadoop.

rm -rf ./input
hdfs dfs -rm -r input output

echo "All input/output files have been removed."