FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=rendezvous-rest/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV USE_PROFILE dev
ENTRYPOINT ["java","-Dspring.profiles.active=${USE_PROFILE}", "-jar","/app.jar"]
