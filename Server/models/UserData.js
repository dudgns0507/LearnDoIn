var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userData = new Schema({
    id: String,
    pw: String,
    email: String,
    name: String,
    study_time: {
        start_time: [Date],
        finish_time: [Date]
    },
    create_date: { type: Date, default: Date.now },
    wordLists: [Schema.Types.ObjectId]
});

module.exports = mongoose.model('UserData', userData);
