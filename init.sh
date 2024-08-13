#!/bin/bash

SCRIPT_FILE=/opt/bitnami/spark/initSpark/initDatasets.scala
SOURCE_DATASET_FILE=./processedDataset/processed_merged.csv/*.csv
TARGET_DATASET_FILE=./elk/logstash/data/merged_data.csv

docker compose up -d
docker exec spark bash -c "spark-shell -i $SCRIPT_FILE > /dev/null 2>&1"
docker compose down

cp $SOURCE_DATASET_FILE $TARGET_DATASET_FILE
cp ./processedDataset/processed_food.csv/*.csv ./elk/logstash/data/food_prices.csv
