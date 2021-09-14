const express = require('express');
const app = express();

app.get('/', (req, res) => {
  for (let i = 1; i <= 100; i++) {
    fib(i).then(r => console.log(`fib(${i}) = ${r}`));
  }
  const name = process.env.NAME || 'World';
  res.send(`Hello ${name}!\n`);
});

const port = process.env.PORT || 8080;
app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});

async function fib(n) {
	if (n > 1) {
    return (await fib(n - 2)) + (await fib(n - 1));
	}
  return n;
}
