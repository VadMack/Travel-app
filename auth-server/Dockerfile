FROM openjdk:18-jdk-alpine
ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests
ENTRYPOINT ["java","-jar","target/auth-server-0.0.1-SNAPSHOT.jar"]