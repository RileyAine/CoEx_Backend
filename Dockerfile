
FROM openjdk:17-jdk-slim

WORKDIR /opt/app

ARG JAR_FILE=target/backend-1.0.2-SNAPSHOT.jar

# cp backend-1.0.0-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]

# sudo docker run -d -p 80:8787 -t frontend_coex:1.0.0