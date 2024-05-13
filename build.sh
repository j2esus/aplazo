#!/bin/bash
echo "compiling credit-line project..."
cd credit-line
mvn clean compile
mvn package -DskipTests
cd ..

echo "compiling customer project..."
cd customer
mvn clean compile
mvn package -DskipTests
cd ..

echo "compiling loan project..."
cd loan
mvn clean compile
mvn package -DskipTests
cd ..

echo "compiling scheme project..."
cd scheme
mvn clean compile
mvn package -DskipTests
cd ..

echo "Build complete."
