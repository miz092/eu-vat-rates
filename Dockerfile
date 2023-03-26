FROM maven:latest AS build
COPY . /app
WORKDIR /app
RUN mvn package

FROM openjdk:latest
COPY --from=build /app/target/vatratesapi-0.0.1-SNAPSHOT.jar /app/euVatRate-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/app/euVatRate-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
