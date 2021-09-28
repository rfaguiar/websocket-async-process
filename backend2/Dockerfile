FROM openjdk:11-jdk-slim as builder

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

FROM openjdk:11-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/program.jar
COPY ./cmd/wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

ARG PORT=8081
ENV PORT=$PORT

EXPOSE $PORT

CMD java -Duser.timezone=America/Sao_Paulo -Dserver.port=$PORT -jar program.jar
