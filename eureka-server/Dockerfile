FROM openjdk:18-jdk-alpine
ADD . ..
WORKDIR .
RUN ./mvnw package -DskipTests
EXPOSE 8761
ENTRYPOINT ["java","-jar","eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar"]
