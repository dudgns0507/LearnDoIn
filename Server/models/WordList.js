var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var wordList = new Schema({
    wordList: [
        {
            key: String,
            value: String,
            sort: Number,
            count: Number,
            repete: Number,
            memorize: Number,
            time: Number
        }
    ]
});

module.exports = mongoose.model('WordList', wordList);
