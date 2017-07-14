
const express = require("express");
const app = express();
const fs = require("fs");

app.post('/getContacts', (req, res) => {
  fs.readFile("json/contacts.json", "utf8", function(err, data) {
    res.json(JSON.parse(data));
  })
});

app.post('/getNews', (req, res) => {
  fs.readFile("json/news.json", 'utf8', function(err, data) {
    res.json(JSON.parse(data));
  })
});

app.get('/', (req, res) => {
  const html = `
    <html>
      <body>
        <h1>Server online</h1>
      </body>
    </html>
  `;

  res.setHeader('Content-Type', 'text/html');
  res.write(html);
  res.end();
});

const port = () => Number(process.argv.slice(-1)[0]);
const server = app.listen(port(), () => {
  console.log(`UWS Server Online ${JSON.stringify(server.address())}`);
});
