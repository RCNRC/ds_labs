#!/bin/bash

export PATH=$PATH:/usr/local/hadoop/bin/

if [ ! -f apache-flume-1.11.0-bin.tar.gz ]; then
  wget --no-check-certificate https://dlcdn.apache.org/flume/1.11.0/apache-flume-1.11.0-bin.tar.gz
  tar xvzf apache-flume-1.11.0-bin.tar.gz
  rm -rf /apache-flume-1.11.0-bin/conf
  mv /conf /apache-flume-1.11.0-bin
else
  echo "Flume already exists, skipping..."
fi

hadoop dfs -rm -r out_data
hadoop dfs -rm -r input_data
hadoop fs -mkdir hdfs://localhost:9000/user/root/input_data
rm -rf ./input_data
mkdir input_data

export JAVA_OPTS="-Xms100m -Xmx2000m -Dcom.sun.management.jmxremote"

flume_func() {
  #sleep 10s
  ./apache-flume-1.11.0-bin/bin/flume-ng agent -n LogAgent -c conf -f apache-flume-1.11.0-bin/conf/flume-hdfs-sink.conf
}
flume_func &

tmp_string="nothing"
catalog_string="1=opened and read,2=opened and preview read,3=don't interact" # не знаю, нужно ли записывать этот справочник перед строками, он занимает много места.
function make_valid_string(){
	hours=$(($RANDOM%24))
  if [ $hours -lt 10 ]; then
    hours="0$hours"
  fi
	minutes=$(($RANDOM%60))
  if [ $minutes -lt 10 ]; then
    minutes="0$minutes"
  fi
	seconds=$(($RANDOM%60))
	if [ $seconds -lt 10 ]
	then
		seconds="0$seconds"
	fi
	NewsID=$(($RANDOM%100))
	UserID=$(($RANDOM%200))
	Interact=$(($RANDOM%3+1))
  tmp_string="[$catalog_string]$NewsID $UserID $hours:$minutes:$seconds $Interact"
}

for i in {1..10}
do
touch "./input_data/input.$i"
	for j in {1..5000}
	do
		make_valid_string
		echo "$tmp_string" >> "./input_data/input.$i"
	done
	echo "Generated successfully ./input_data/input.$i"
done
echo "Recreated input data."

#hadoop fs -put /input_data hdfs://localhost:9000/user/root/input_data

