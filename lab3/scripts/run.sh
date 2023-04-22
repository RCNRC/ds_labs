#!/usr/bin/env bash

sudo -i service elasticsearch start
sudo service grafana-server start
/usr/share/logstash/bin/logstash -f /usr/share/logstash/bin/work.conf
#java -jar lab3-1.0-SNAPSHOT-jar-with-dependencies.jar
