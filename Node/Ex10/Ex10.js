/*
File Name : Ex10.js
Author : Ilay Serr and Roni Ben-moshe
*/

const express = require('express');
const app = express();
const server = require('http').createServer(app);
const io = require('socket.io')(server);
const alphavantage = require('alphavantage')({ key: 'ZTC8GK4V3U9A4GZO' });

// Process.env.PORT let the port be set by Heroku
let port = process.env.PORT || 5000;
//let coin = 'xrp';
let res , last_refreshed;

io.on('connect', function (socket) {
	socket.on('new quotes', function (data) {
	coin = data;
	console.log("checking for coin " + coin)
	});

  	let postRate = setInterval( function() {
	  	if (!socket) {
	  		console.log(`Couldn't connect socket`);
	      	return;
	  	}
	  	if (!coin) {
	  		console.log(`Couldn't find coin`);
	  		socket.emit("enter coin name")
	      	return;
	  	}

	 // Output example: 
	 // { 'Realtime Currency Exchange Rate': 
	 //   { '1. From_Currency Code': 'btc',
	 //     '2. From_Currency Name': null,
	 //     '3. To_Currency Code': 'usd',
	 //     '4. To_Currency Name': null,
	 //     '5. Exchange Rate': '15118.62571180',
	 //     '6. Last Refreshed': '2018-01-08 20:03:13',
	 //     '7. Time Zone': 'UTC' } }

	 	alphavantage.forex.rate(coin, 'usd').then(data => {
	      	res = Object.values(data['Realtime Currency Exchange Rate'])[4];
	  		socket.emit('postRate', res);
	  	});
    }, 15000);
  
  socket.on('disconnect',() => {
    clearInterval(postRate);
  });
});


// Let us listen to the port we want
app.listen(port, function() {
    console.log('My app is running on http://localhost:' + port);
});


