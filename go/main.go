package main

import (
        "fmt"
        "log"
        "net/http"
        "os"
)

func main() {
        http.HandleFunc("/", handler)

        port := os.Getenv("PORT")
        if port == "" {
                port = "8080"
                log.Printf("defaulting to port %s", port)
        }
        log.Printf("Listening on port %s", port)
        if err := http.ListenAndServe(":"+port, nil); err != nil {
                log.Fatal(err)
        }
}

func handler(w http.ResponseWriter, r *http.Request) {
        go func() {
            for i := 0; i < 100; i++ {
                log.Printf("fib(%d) = %d\n", i, fib(i))
            }
        }()
        name := os.Getenv("NAME")
        if name == "" {
                name = "World"
        }
        fmt.Fprintf(w, "Hello %s!\n", name)
}

func fib(n int) int {
    if n > 1 {
        return fib(n-2) + fib(n-1)
    }
    return n
}
