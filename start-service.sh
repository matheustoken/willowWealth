#!/bin/bash

APP_NAME="willow"
JAR_FILE="target/willow-0.0.1-SNAPSHOT.jar"
PORT=9999

echo "Starting $APP_NAME on port $PORT..."

PID=$(lsof -t -i :$PORT)
if [ -z "$PID" ]; then
    echo "Port $PORT is free."
else
    echo "Port $PORT is occupied by PID: $PID. Killing process..."
    kill -9 $PID
    sleep 2
fi

echo "Building application..."
mvn clean package -DskipTests

if [ ! -f "$JAR_FILE" ]; then
    echo "Error: $JAR_FILE not found."
    exit 1
fi

echo "Launching service..."
nohup java -jar $JAR_FILE --server.port=$PORT > service.log 2>&1 &

sleep 2

echo "Service started in background."
echo "Log file: service.log"
echo "URL: http://localhost:$PORT"