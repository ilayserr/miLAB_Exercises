/*
File Name :  Ex8_server.js
Author : Ilay Serr
Email : ilay92@gmail.com
*/

let express = require('express');
let app = express();

// Process.env.PORT let the port be set by Heroku
let port = process.env.PORT || 5000;

// Get time
app.get('/getTime', function(req, res){
  let dateTime = require('node-datetime');
  let dt = dateTime.create();
  let formatted = dt.format('Y-m-d H:M:S');
  res.send(formatted);
});

// Read file
app.get('/getFile', function(req, res) {
  let fileName = req.query.filename;
  fs = require('fs')
  fs.readFile(fileName , 'utf8', function (err,data) {
    if (err) {
      res.send("couldn't find file");
      return console.log(err);
    }
    res.send(data);
  });
});


// Let us listen to the port we want
app.listen(port, function() {
    console.log('My app is running on http://localhost:' + port);
});

module.exports = app;

