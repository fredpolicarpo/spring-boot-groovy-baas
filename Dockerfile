FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp

# Add docker-compose-wait tool -------------------
ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

RUN mkdir /opt/app
WORKDIR /opt/app

ARG JAR_FILE=build/libs/baas-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "app.jar"]