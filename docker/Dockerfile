FROM openjdk:11-jre-slim

RUN apt-get update && apt-get install -y curl

RUN mkdir -p /var/app
WORKDIR /var/app

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

COPY build/libs/mastermind-1.0-SNAPSHOT.jar /var/app
COPY docker/start.sh /var/app/docker/start.sh
RUN chmod +x /var/app/docker/start.sh
EXPOSE 8080
CMD /wait && /var/app/docker/start.sh