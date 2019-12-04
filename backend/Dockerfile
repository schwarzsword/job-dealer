FROM openjdk:8-jre-alpine
WORKDIR /app/backend
COPY target/job-dealer.jar ./job-dealer.jar
COPY ./entrypoint.sh ./entrypoint.sh
CMD ["/usr/bin/java", "-jar", "job-dealer.jar"]
