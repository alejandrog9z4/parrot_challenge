FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/parrot-0.0.1-SNAPSHOT.jar /app/parrot-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true","-jar", "parrot-0.0.1-SNAPSHOT.jar"]