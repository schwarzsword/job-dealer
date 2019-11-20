FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 6565
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/job-dealer.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]