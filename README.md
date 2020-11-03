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

The connection data is located in the file [`application.properties`](https://github.com/fredpolicarpo/spring-boot-groovy-baas/blob/main/src/main/resources/application.properties)

If you will use some pre-existent database, just update the [`application.properties`](https://github.com/fredpolicarpo/spring-boot-groovy-baas/blob/main/src/main/resources/application.properties) 
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
The architecture of this project is inspired on two references:

 - [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
 - [Ports and Adapters](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
 
## Overview

![architecture overview image](https://github.com/fredpolicarpo/spring-boot-groovy-baas/blob/main/Architecture.png)
 
## Layers
A brief explanation about the main layers of this architecture.

### Business
The business layers must be agnostic of any framework and must contain all application and business rules.

This way the code of this layer should be reused on any version of the BaaS, regardless the details like: Framework, User Interfaces or Databases.

### UI
The UI Layer define data structure to move data in and out the business layer, with the `*Request` and `*Response` POJO classes.

The root of `ui` layer should have structures agnostic of the UI type(we api, CLI, mobile, etc..)

This layer must be framework agnostic as well.

#### API

The API layer contains details of UI regarding the Web API presentation mode, like the http status code.

This layer define data structures that extends the base ones (on the root `ui` layer), with data regarding web api, 
and also have presenters with the logic for showing the responses on a Web API.
