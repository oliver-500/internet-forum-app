FROM openjdk:17-alpine
WORKDIR /opt/app/
COPY /target/forum_web_server-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/opt/app/forum_web_server-0.0.1-SNAPSHOT.jar"]