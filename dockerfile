FROM openjdk:17-jdk-slim-buster
COPY target/sts-upload-download-manager-0.0.1-SNAPSHOT.jar sts-upload-download-manager.jar
ENTRYPOINT ["java","-jar","sts-upload-download-manager.jar"]