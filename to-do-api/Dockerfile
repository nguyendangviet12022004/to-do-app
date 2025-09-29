FROM maven:3.9.10-amazoncorretto-21-alpine AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:21-jdk-slim  AS runtime

WORKDIR /app

COPY --from=build /build/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

