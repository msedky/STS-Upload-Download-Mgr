FROM openjdk:8-jdk-alpine
VOLUME /tmp sts-upload-download-manager-0.0.1-SNAPSHOT.jar sts-upload-download-manager.jar
ENTRYPOINT ["java","-jar","sts-upload-download-manager.jar"]