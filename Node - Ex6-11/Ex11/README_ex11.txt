To check the app , run npm start.
open http://localhost:5000/

Using postman:
* To enter new song:
	Enter body in the shape of json: {"name": "tuduBom", "artist": "Static", "genre": "Pop"}

* To search song: 
	open http://localhost:5000/?name=tuduBom

* To search all songs from a specific artist: 
	open http://localhost:5000/?artist=Static

* To search all songs from a specific genre: 
	open http://localhost:5000/?genre=Pop

* To update song:
take the song id you want to change and enter the following JSON in the params using postman:
{"id": "5a5f787577c4a02bb40f91ab" , "name": "new song", "artist": "some artist", "genre": "rock"}

* To delete song:
enter song id in postman using DELETE