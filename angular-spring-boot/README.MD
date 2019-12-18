Angular-SpringBoot-MyBatis-Liquibase-Template

Stack:
* Angular 5
* Spring Boot
* MyBatis
* Liquibase
* Gradle

# Project build:

`cd [projectPath]/common-angular-frontend`

`npm install`

`cd [projectPath]`

`gradle wrapper`

`gradlew clean build`

# Launching

`execute in terminal command *java -jar [path to result jar]*`

`then open link in browser http://localhost:8123/#/manage`

# Using with docker container

* `build project via gradle`
* `cd [projectPath]/backend`
* execute command `docker build -t isnagornov/angular-spring-boot .`
* execute command `docker run -p 8123:8123 -t isnagornov/angular-spring-boot .`, app should run at localhost:8123
* execute command `docker ps -a` to check container health
* execute command `docker stop [CONTAINER_ID]` to stop container
