FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV spring_datasource_url=jdbc:postgresql://10.203.114.27:5432/test_db
ENV spring_datasource_username=postgres
ENV spring_datasource_password=postgres
ENV spring_datasource_driver-class-name=org.postgresql.Driver
EXPOSE 8123
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
