# Angular-JavaEE-Template

Stack:
* Angular 5
* JavaEE
* Liquibase
* Gradle
* GlassFish

# Project build:

`cd [projectPath]/common-angular-frontend`

`npm install`

`cd [projectPath]`

`gradle wrapper`

`gradlew clean build`

# Launching

`before launching create DB with name "test_db, for using db connection add JNDI in GlassFish settings"`

`after build deploy war archive to GlassFish*`

`then open link in browser http://localhost:8080/serviceApi/#/manage`