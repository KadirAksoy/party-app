FROM maven:3.6.3-openjdk-17 AS build

WORKDIR /

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

COPY src ./src
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix mvnw && apt-get --purge remove -y dos2unix && rm -rf /var/lib/apt/lists/*

RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

EXPOSE 8080
ENTRYPOINT ["java","-jar","/target/party-app.jar"]