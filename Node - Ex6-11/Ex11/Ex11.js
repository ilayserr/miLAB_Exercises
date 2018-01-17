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

// Enter a new song with the parameters given in a shape of JSON, calls the EnterSong function below
app.post('/', function(req, res) {
    if(!req.body.name || !req.body.artist || !req.body.genre) 
      res.status(400).send({message: "Doesn't have parameters, please enter them in the shape of JSON"});
    else 
      let status = EnterSong(req.body.name, req.body.artist, req.body.genre, res);
});

// Enter a new song with the parameters given
function EnterSong(name, artist, genre, res) {
  let song = {name: name, artist: artist, genre: genre};
  db.collection('songsCol').insert([song], function(err, database) {
        if (err) res.send("can't add the song")
        else (res.send("Added the song "));
    });
}

// Activates the update function below
app.put('/', function(req, res) {
    updateSong(req.body.id, req.body.name, req.body.artist, req.body.genre, res);
});

// Updates the song by a given ID
function updateSong(id, name, artist, genre, res) {
  let song = {
    name: name,
    artist: artist,
    genre: genre
  };

// Updates the database
db.collection('songsCol').updateOne({_id: ObjectId(id)}, {$set: song}, function (err, numUpdated) {
    if(err) res.send("Error update song " + id);
    else if(numUpdated) res.send("Succedded to update");
    else res.send("Can't find song");
  });
}

// Activate the read functions below
app.get('/', function(req, res) {
  if(req.query.name) {
    readSong(req.query.name, res);
  } else if(req.query.artist) {
    readArtist(req.query.artist, res);
  } else if(req.query.artist) {
    readGenre(req.query.genre, res);
  } else {
    res.status(400).send({message: "Doesn't have songs answering this data"});
  }
});

// Read the data for the song name given, uses the printOneSong method
function readSong(song, res) {
  db.collection('songsCol').findOne({name: song}, function(err, document) {
    if(err || !document) res.send("Cannot find a song named " + song);
    else {
      printOneSong(document, res);
      res.end();
    }
  });
}

// Read all the songs related to a specific artist, uses the printOneSong method
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

// Read all the songs related to a specific genre, uses the printOneSong method
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

// Print the song details
function printOneSong(song, res) {
  res.write(`Song name: ${song.name}\n`);
  res.write(`Song artist: ${song.artist}\n`);
  res.write(`Song genre: ${song.genre}\n`);
  res.write(`Song id: ${song._id}\n\n`);
}

// Activate the deleteSong function
app.delete('/:id' , function(req, res) {
    let SongId = req.params.id;
    deleteSong(SongId, res);
});

// Delete song from the database
function deleteSong(SongId, res) {
  db.collection('songsCol').deleteOne({ _id: ObjectId(SongId) }, function(err, res) {
      res.send("Deleted song " + SongId);
  });
}

// Connects to mongodb using mlab.com data base
MongoClient.connect(mongoUrl, (err, database) => {
  if (err) 
    return console.log(err)
  
  console.log("Connected to the server");
  if (err) return console.log(err)
  db = database.db("songs")

  // Listens to the app on port 5000
  app.listen(port, function() {
      console.log('My app is running on http://localhost:' + port);
  });

});


