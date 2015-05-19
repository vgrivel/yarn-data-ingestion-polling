# yarn-data-ingestion-polling
Poll a datasource with Yarn and store data on the hadoop cluster. It is based on https://github.com/daplab/yarn-starter

# Getting Started

## Configuration
Open the conf/config.conf file and modify it with your settings.

## Building
```
mvn clean install
```

## Running
Run the script inside the project folder
```
 ./src/main/scripts/start-poller-ingestion-app.sh daplab-wn-22.fri.lan:2181 conf/config.conf
```
## On running operation


If you want to list all your yarn application, do it with the command:
```
yarn application -list
```

Logs of the application are available only when the application is closed.
First, kill the application.
```
yarn application -kill application_1426168278944_0001
```
Copy the distributed log on one file. It will simplify the error or message searching.
```
yarn logs -applicationId application_1430914769571_0001 >logs
```
#File processing
By default, no processing is made to the data. They are directly written to the disk. If you want to process, just create a class that implements FileProcessing and fill the process method.
The processing method if useful for changing the file format (to CSV, JSON, ...) or removing headers, rows, null value, etc.

