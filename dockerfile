FROM maven:3.8.6-openjdk-18-slim

WORKDIR /app

COPY pom.xml .
RUN mvn clean package -DskipTests

COPY target/smartform-0.0.1-SNAPSHOT.jar /app/smartform.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "smartform.jar"]