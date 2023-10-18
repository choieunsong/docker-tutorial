FROM eclipse-temurin:17-alpine AS build

# Build a Java Runtime Environment with only what we need
RUN apk add --no-cache binutils && \
    $JAVA_HOME/bin/jlink \
      --add-modules java.base \
      --strip-debug \
      --no-man-pages \
      --no-header-files \
      --compress=2 \
      --output=/javaruntime

# Build our program
RUN mkdir /tmp/build
COPY Hello.java /tmp/build
RUN $JAVA_HOME/bin/javac /tmp/build/Hello.java

# Now we can start from a very minimal image
FROM alpine:latest AS production

# Copy over the JRE we built before
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=build /javaruntime $JAVA_HOME

# Copy the compiled program
COPY --from=build /tmp/build /opt/app

# We will run the program as the entrypoint: we can
# do 'docker run ... name' that way.
ENTRYPOINT ["java", "-cp", "/opt/app", "Hello"]
