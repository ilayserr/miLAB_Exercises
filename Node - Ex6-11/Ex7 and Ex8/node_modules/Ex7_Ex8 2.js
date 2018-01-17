/*
File Name : Ex7_Ex8.js
Author : Ilay Serr
Email : ilay92@gmail.com
*/

const express = require('express');
const fs = require('fs');
const app = express();

// Process.env.PORT let the port be set by Heroku
let port = process.env.PORT || 5000;

//***********  EX7  *************//

// EX7 - Get time
app.get('/getTime', function(req, res){
  let dateTime = require('node-datetime');
  let dt = dateTime.create();
  let formatted = dt.format('Y-m-d H:M:S');
  res.send(formatted);
});

// EX7 - Read file
app.get('/getFile', function(req, res) {
  let fileName = req.query.filename;
  fs.readFile(fileName , 'utf8', function (err,data) {
    if (err) {
      res.send("couldn't find file");
      return console.log(err);
    }
    res.send(data);
  });
});

//***********  EX8  *************//

// EX8 - Get file.txt
app.get('/files/example-file', function(req, res){
  const readStream = fs.createReadStream('file.txt');
  readStream.pipe(res);
});

// EX8 - Get Shells.jpg
app.get('/files/example-image', function(req, res){
  const readStream = fs.createReadStream('Shells.jpg');
  readStream.pipe(res);
});

// Let us listen to the port we want
app.listen(port, function() {
    console.log('My app is running on http://localhost:' + port);
});

module.exports = app;

