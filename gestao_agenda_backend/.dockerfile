FROM openjdk:11-jdk-alpine

WORKDIR /app

COPY target/gestao_agenda_backend.jar .
EXPOSE 8080


ENV AWS_REGION=us-west-2
ENV AWS_ACCESS_KEY_ID=your_access_key_id
ENV AWS_SECRET_ACCESS_KEY=your_secret_access_key


CMD ["java", "-jar", "myapp.jar"]
