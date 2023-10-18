FROM eclipse-temurin:17-alpine AS build

WORKDIR /tmp
COPY Hello.java .
RUN javac Hello.java

FROM eclipse-temurin:17-jre-alpine AS production

RUN mkdir /opt/application
COPY --from=build /tmp/Hello.class /opt/application
CMD ["java", "-cp", "/opt/application", "Hello"]
