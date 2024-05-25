FROM openjdk:17-alpine
WORKDIR /opt/app/
COPY /target/forum_siem-0.0.1-SNAPSHOT.jar forum_siem.jar
ENTRYPOINT ["java", "-jar", "/opt/app/forum_siem.jar"]