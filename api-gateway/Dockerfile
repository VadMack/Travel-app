FROM openjdk:18-jdk-alpine
ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests
EXPOSE 8765
ENTRYPOINT ["java","-jar","target/api-gateway-0.0.1-SNAPSHOT.jar"]