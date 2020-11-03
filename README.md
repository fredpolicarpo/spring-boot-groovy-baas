# Simple Bank as a Service
### A simple BaaS 
This project runs a Web API with Banking operations

# Setting Up
This section will explain how to configure the environment to run the `BaaS` API

## Install Java 11
It is recommend to use [SKDMAN!](https://sdkman.io) to manage the JDK versions in your environment.

To install it go to [SDKMAN! install instructions](https://sdkman.io/install).

After that, use some version of Java 11:
 - Show available versions 
   - `sdk list java`
 - Install Java 11 (for instance)
   - `sdk install java 11.0.9.hs-adpt`
 - Use Java 11 (for instance)
   - `sdk use java 11.0.9.hs-adpt`

## Build and test the project
### Run tests
The following command will build the project and run all tests:

`./gradlew test`

### Verify code coverage 
Open the [JaCoCo](https://www.jacoco.org/jacoco/) report at `build/reports/jacoco/test/html/index.html`.


# Run Local with Gradlew
This project requires an instance of the database [PostgreSQL](https://www.postgresql.org/). 

The connection data is located in the file [`application.properties`](https://github.com/fredpolicarpo/spring-boot-seed/blob/main/src/main/resources/application.properties)

If you will use some pre-existent database, just update the [`application.properties`](https://github.com/fredpolicarpo/spring-boot-seed/blob/main/src/main/resources/application.properties) 
file with the connection data.

If you dont have an instance of PostgreSQL, you can run it locally using [Docker](https://www.docker.com/).

## Run PostgreSQL with Docker
This command will up an instance of PostgreSQL with the properly configuration:

`docker-compose up -d`

### Create the database
You need to connect on the postgres `default database` (`jdbc:postgresql://localhost:5432/postgres`), and create the `baas` database.
Execute the following command:

`create database baas;`

## Run Application
Now, you can run the application. (This command will execute the database migrations)

`./gradlew run`

# Project Structure, Architecture and  Engineering Principles
//TODO