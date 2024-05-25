FROM openjdk:17-alpine
WORKDIR /opt/app/
EXPOSE 9002
COPY target/forum_jwt_controller-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/opt/app/forum_jwt_controller-0.0.1-SNAPSHOT.jar"]