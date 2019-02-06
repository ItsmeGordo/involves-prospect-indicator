FROM openjdk:8-jdk-alpine
COPY target/prospectIndicator-*.jar app.jar
ADD src/main/resources/files src/main/resources/files
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]