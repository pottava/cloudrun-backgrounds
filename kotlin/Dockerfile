FROM gcr.io/cloud-builders/gradle as builder
WORKDIR /home/gradle/
COPY settings.gradle.kts .
COPY build.gradle .
COPY src ./src
RUN gradle clean build --no-daemon

FROM gcr.io/distroless/java-debian10:11
COPY --from=builder /home/gradle/build/libs/cloudrun-sample.jar /sample.jar
CMD ["-jar", "-Djava.security.egd=file:/dev/./urandom", "/sample.jar" ]
