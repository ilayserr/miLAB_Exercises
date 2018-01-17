/*
File Name : Ex11.js
Author : Ilay Serr
Email : ilayserr@gmail.com
*/

const bodyParser= require('body-parser');
const MongoClient = require('mongodb').MongoClient;
const express = require('express');
const app = express();
const mongoUrl = 'mongodb://ilay921:ilay1111@ds159707.mlab.com:59707/songs';
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
const ObjectId = require('mongodb').ObjectId;
let port = process.env.PORT || 5000;
let db;


app.post('/', function(req, res) {
    if(!req.body.name || !req.body.artist || !req.body.genre) {
      res.status(400).send({message: "Doesn't have parameters" + req.body + "  " + req.body[0] });
    } else {
      let status = EnterSong(req.body.name, req.body.artist, req.body.genre, res);
    }
});



function EnterSong(name, artist, genre, res) {
  let song = {name: name, artist: artist, genre: genre};
  db.collection('songsCol').insert([song], function(err, database) {
        if (err) res.send("can't add the song")
        else (res.send("Added the song "));
    });
}

app.put('/', function(req, res) {
    updateSong(req.body.id, req.body.name, req.body.artist, req.body.genre, res);
});

function updateSong(id, name, artist, genre, res) {
  let song = {
    name: name,
    artist: artist,
    genre: genre
  };

db.collection('songsCol').updateOne({_id: ObjectId(id)}, {$set: song}, function (err, numUpdated) {
    if(err) res.send("Error update song " + id);
    else if(numUpdated) res.send("Succedded to update");
    else res.send("Can't find song");
  });
}


app.get('/', function(req, res) {
  if(req.query.name) {
    readSong(req.query.name, res);
  } else if(req.query.artist) {
    readArtist(req.query.artist, res);
  } else if(req.query.artist) {
    readGenre(req.query.genre, res);
  } else {
    res.status(400).send({message: "Doesn't have parameters"});
  }
});

function readSong(song, res) {
  db.collection('songsCol').findOne({name: song}, function(err, document) {
    if(err || !document) res.send("Cannot find a song named " + song);
    else {
      printOneSong(document, res);
      res.end();
    }
  });
}


function readArtist(artist, res) {
  db.collection('songsCol').find({artist: artist}).toArray((err, results) => {
    if(err || !results[0]) res.send("Error finding songs");
    else {
     res.write(`Songs found:\n`);
     results.forEach(doc => printOneSong(doc, res));
     res.end();
    }
  });
}

function readGenre(genere, res) {
  db.collection('songsCol').find({genre: genere}).toArray((err, results) => {
    if(err || !results[0]) res.send("Error finding songs");
    else {
     res.write(`Songs found:\n`);
     results.forEach(doc => printOneSong(doc, res));
     res.end();
    }
  });
}

function printOneSong(song, res) {
  res.write(`Song id: ${song._id}\n`);
  res.write(`Song name: ${song.name}\n`);
  res.write(`Song artist: ${song.artist}\n`);
  res.write(`Song genre: ${song.genre}\n`);
  res.write(`\n`);
}

app.delete('/:id' , function(req, res) {
    let SongId = req.params.id;
    deleteSong(SongId, res);
});

function deleteSong(SongId, res) {
  db.collection('songsCol').deleteOne({ _id: ObjectId(SongId) }, function(err, res) {
      res.send("Deleted song " + SongId);
  });
}


MongoClient.connect(mongoUrl, (err, database) => {
  if (err) 
    return console.log(err)
  console.log("Connected to the server");
  if (err) return console.log(err)
  db = database.db("songs")
  db.collection('songsCol').createIndex({ name: 1 });
  db.collection('songsCol').createIndex({ artist: 1 });
  db.collection('songsCol').createIndex({ genre: 1 });

  app.listen(port, function() {
      console.log('My app is running on http://localhost:' + port);
  });

});


