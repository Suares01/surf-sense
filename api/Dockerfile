FROM --platform=linux/amd64 maven as build

COPY . .

RUN mvn clean package -D skipTests

FROM --platform=linux/amd64 openjdk:21-jdk-slim

ENV DATASOURCE_URL=${DATASOURCE_URL}
ENV DATASOURCE_USERNAME=${DATASOURCE_USERNAME}
ENV DATASOURCE_PASSWORD=${DATASOURCE_PASSWORD}
ENV CACHE_PASSWORD=${CACHE_PASSWORD}
ENV CACHE_HOST=${CACHE_HOST}
ENV CACHE_PORT=${CACHE_PORT}
ENV PORT=${PORT}

COPY --from=build /target/api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
