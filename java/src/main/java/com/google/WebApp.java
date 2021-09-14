package com.google;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.lang.Thread;
import java.lang.Runnable;
import java.lang.InterruptedException;

public class WebApp {

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", handler -> {
            for (int i=0; i<100; i++) {
                new Thread((new Runnable() {
                    int n;
                    public Runnable arg(int n) {
                          this.n = n;
                          return this;
                    }
                    @Override
                    public void run() {
                        int answer = fib(n);
                        System.out.printf("fib(%d) = %d\n", n, answer);
                    }
                }).arg(i)).start();
            }
            byte[] response = "hello, world\n".getBytes();
            handler.sendResponseHeaders(200, response.length);
            try (OutputStream os = handler.getResponseBody()) {
                os.write(response);
            }
        });

        System.out.println("Listening on port " + port);
        server.start();
    }

    private static int fib(int n) {
        if (n > 1) { 
            return fib(n-2) + fib(n-1);
        }
        return n;
    }
}
