package com.example.hello

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*

fun main(args: Array<String>) {
    val target = System.getenv("TARGET") ?: "World"
    val port = System.getenv("PORT") ?: "8080"
    embeddedServer(Netty, port.toInt()) {
        routing {
            get("/") {
                GlobalScope.launch {
                    for (i in 0..100) {
                        println("fib(%d) = %d".format(i, fib(i)))
                    }
                }
                call.respondText("Hello $target!\n", ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}

fun fib(n: Int): Int {
    return if (n > 1) fib(n - 1) + fib(n - 2) else n
}
