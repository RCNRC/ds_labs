#!/bin/bash

sudo apt-get update -y
sudo apt-get upgrade -y
sudo apt-get install vim -y

wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo gpg --dearmor -o /usr/share/keyrings/elasticsearch-keyring.gpg
sudo apt-get install apt-transport-https -y
echo "deb [signed-by=/usr/share/keyrings/elasticsearch-keyring.gpg] https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee /etc/apt/sources.list.d/elastic-7.x.list
sudo apt-get update -y
sudo apt-get install elasticsearch
rm /etc/elasticsearch/elasticsearch.yml
mv elasticsearch.yml /etc/elasticsearch
chmod -R 0777 /usr/share/elasticsearch


sudo apt-get update -y
sudo apt-get install logstash
mv work.conf /usr/share/logstash/bin

sudo apt-get update -y
sudo apt-get upgrade -y
sudo apt-get install -y apt-transport-https && sudo apt-get install -y software-properties-common wget && sudo wget -q -O /usr/share/keyrings/grafana.key https://apt.grafana.com/gpg.key
echo "deb [signed-by=/usr/share/keyrings/grafana.key] https://apt.grafana.com stable main" | sudo tee -a /etc/apt/sources.list.d/grafana.list
sudo apt-get update -y
sudo apt-get install grafana
sudo apt-get install grafana-enterprise
sudo update-rc.d grafana-server defaults

/flume.sh
