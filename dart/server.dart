import 'dart:convert';
import 'dart:io';
import 'dart:async';
import 'dart:isolate';

import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart' as io;
import 'package:shelf_router/shelf_router.dart' as shelf_router;

Future main() async {
  final port = int.parse(Platform.environment['PORT'] ?? '8080');
  final cascade = Cascade().add(_router);

  final server = await io.serve(
    logRequests().addHandler(cascade.handler),
    InternetAddress.anyIPv4,
    port,
  );
  print('Serving at http://${server.address.host}:${server.port}');
}

final _router = shelf_router.Router()
  ..get('/', _helloWorldHandler);

Response _helloWorldHandler(Request request) {
  final future = Future(() {
    for (var i = 0; i < 100; i++) {
      print('fib($i) = ${fib(i)}');
    }
  });
  return Response.ok('Hello, World!\n');
}

int fib(int n) {
  return n < 2 ? n : (fib(n - 1) + fib(n - 2));
}
