# Build with `docker build -t aktify .`
# Run with something like this: `docker run -m512M --cpus 4 -it -p 8080:8080 -e SERVICE_DB_HOST=192.168.1.9 --rm butterfly`

FROM adoptopenjdk/openjdk12-openj9:alpine-slim

ENV APPLICATION_USER aktify
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./build/libs/aktify.jar /app/aktify.jar
WORKDIR /app

#ENV PORT 8080
EXPOSE $PORT
RUN export PORT=$PORT
RUN echo echoing port: $PORT

CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-Xtune:virtualized", "-jar", "aktify.jar"]
