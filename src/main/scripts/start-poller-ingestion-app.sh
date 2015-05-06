#!/bin/bash

if [ "$1" == "" ]
then
    echo "Usage: $0 <zookeeper_connection_sting>"
    echo " where <zookeeper_connection_sting> is the hostname:port of one of the zookeeper server"
    exit 1
fi

# Generate the classpath
export HADOOP_CLASSPATH=$(find target/lib/ -type f -name "*.jar" | grep -v "yarn-data-ingestion-polling" | paste -sd:)

# Ensure our libs (mainly specific guava version) has precedence over the one provided by hadoop
export HADOOP_USER_CLASSPATH_FIRST=true

# Standard launch. PollerToHDFSCli implements Tool interface.
yarn jar target/yarn-data-*.jar ch.daplab.yarn.poller.PollerToHDFSCli --zk.connect $1 --config $2
