FROM openjdk:18-jdk-alpine
ADD .. /src
WORKDIR /src/eureka-server
RUN ./mvnw package -DskipTests
EXPOSE 8761
ENTRYPOINT ["java","-jar","target/eureka-server-0.0.1-SNAPSHOT.jar"]
