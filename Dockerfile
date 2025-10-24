FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-focal
WORKDIR /app

COPY --from=build /app/target/miaudote-0.0.1-SNAPSHOT.jar miaudote.jar

ENTRYPOINT ["java", "-jar", "miaudote.jar"]