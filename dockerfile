FROM openjdk:17-jdk-slim
COPY target/smartform-0.0.1-SNAPSHOT.jar smartform.jar
ENTRYPOINT ["java", "-jar", "/smartform.jar"]