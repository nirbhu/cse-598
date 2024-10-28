#!/bin/bash

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 21 or later."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Using Maven wrapper..."
    if [[ "$OSTYPE" == "msys" ]]; then
        ./mvnw.cmd spring-boot:run
    else
        ./mvnw spring-boot:run
    fi
else
    mvn spring-boot:run
fi
