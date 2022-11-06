FROM openjdk:17-alpine
VOLUME /opt
COPY target/scheduling-tasks-complete-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]