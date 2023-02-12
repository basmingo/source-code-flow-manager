FROM openjdk:17-jdk-alpine

COPY /build/libs/source-code-flow-manager-application-*.jar /opt/source-code-flow-manager-application/

VOLUME /opt/source-code-flow-manager-application/

WORKDIR /opt/source-code-flow-manager-application/

EXPOSE 8080

ENTRYPOINT java -jar /opt/source-code-flow-manager-application/*.jar
