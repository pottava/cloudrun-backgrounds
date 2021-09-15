const express = require('express');
const app = express();

app.get('/', (req, res) => {
  setTimeout(_fib, 25); // async doesn't work properly ;(
  const name = process.env.NAME || 'World';
  res.send(`Hello ${name}!\n`);
});

const port = process.env.PORT || 8080;
app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});

async function _fib() {
  for (let i = 1; i <= 100; i++) {
    console.log(`fib(${i}) = ${fib(i)}`);
  }
  function fib(n) {
    if (n > 1) {
      return fib(n - 2) + fib(n - 1);
    }
    return n;
  }
}
