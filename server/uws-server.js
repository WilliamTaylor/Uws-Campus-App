
const express = require("express");
const fs = require("fs");

const ip = req => {
  return req.headers['x-forwarded-for'] ||
  req.connection.remoteAddress ||
  req.socket.remoteAddress ||
  req.connection.socket.remoteAddress ||
  'Error getting IP address';
};

const app = express();
app.post('/getContacts', (req, res) => {
  console.log("New /getContacts request from " + ip(req));
  fs.readFile("json/contacts.json", "utf8", function(err, data) {
    res.json(JSON.parse(data));
  })
});

app.post('/getNews', (req, res) => {
  console.log("New /getNews request from " + ip(req));
  fs.readFile("json/news.json", 'utf8', function(err, data) {
    res.json(JSON.parse(data));
  })
})

const server = app.listen(3005, () => {
  console.log(`Uws Server Online ${JSON.stringify(server.address())}`);
});
