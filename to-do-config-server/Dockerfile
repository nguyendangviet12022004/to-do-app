FROM maven:3.9.10-amazoncorretto-21-alpine as BUILD

WORKDIR /build

COPY pom.xml ./

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests


FROM amazoncorretto:21.0.2-alpine3.19 as RUN

WORKDIR /app

COPY --from=BUILD /build/target/*.jar ./app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]