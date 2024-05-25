FROM openjdk:17-alpine
WORKDIR /opt/app/
COPY /target/forum_waf-0.0.1-SNAPSHOT.jar forum_waf.jar
ENTRYPOINT ["java", "-jar", "/opt/app/forum_waf.jar"]