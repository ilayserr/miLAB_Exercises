function rotate(word) {
	let length = word.length;
	for (let i = 0; i < length; i++) {
		console.log(word);
		word = word.substring(length - 1, length) + word.substring(0 , length - 1);
	}
}
rotate("milab");