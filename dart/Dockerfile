FROM dart:stable AS build

WORKDIR /app
COPY pubspec.* ./
RUN dart pub get

COPY . .
RUN dart pub get --offline
RUN dart compile exe server.dart -o server

FROM scratch
COPY --from=build /runtime/ /
COPY --from=build /app/server /app/bin/
CMD ["/app/bin/server"]
