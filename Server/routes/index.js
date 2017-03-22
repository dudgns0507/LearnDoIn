module.exports = function(app, crypto, WordList, UserData)
{
    app.post('/api/signup', function(req, res) {
        UserData.findOne({id: req.body.id}, function(err, data) {
            if(err) return res.status(500).json({error: err});
            else if(data) return res.status(404).json({error: 'Id already exist!'});

            else {
                var shasum = crypto.createHash('sha1');
                var userData = new UserData();
                userData.id = req.body.id;
                userData.pw = shasum.update(req.body.pw).digest('hex');
                userData.email = req.body.email;
                userData.name = req.body.name;

                userData.save(function(err) {
                    if(err){
                        console.error(err);
                        res.json({result: 0});
                        return;
                    }

                    res.json({result: 1});
                });
            }
        })
    });

    app.post('/api/signin', function(req,res){
        var shasum = crypto.createHash('sha1');
        var id = req.body.id;
        var pw = shasum.update(req.body.pw).digest('hex');

        var findUser = {
            id: id,
            pw: pw
        }

        UserData.findOne(findUser)
            .exec(function (err, user) {
                if (err){
                    res.json({
                        type: false,
                        data: "Error occured " + err
                    });
                } else if (!user) {
                    res.json({
                        type: false,
                        data: "Incorrect email/password"
                    });
                } else if(user) {
                    res.json({
                        type: true,
                        name: user.name,
                        email: user.email,
                        id: user.id,
                        wordLists: user.wordLists,
                        create_date: user.create_date,
                        study_time: user.study_time
                    });
                }
            });
    });

    // GET SINGLE BOOK
    app.get('/api/books/:book_id', function(req, res){
        Book.findOne({_id: req.params.book_id}, function(err, book){
            if(err) return res.status(500).json({error: err});
            if(!book) return res.status(404).json({error: 'book not found'});
            res.json(book);
        })
    });

    // GET BOOK BY AUTHOR
    app.get('/api/books/author/:author', function(req, res){
        Book.find({author: req.params.author}, {_id: 0, title: 1, published_date: 1},  function(err, books){
            if(err) return res.status(500).json({error: err});
            if(books.length === 0) return res.status(404).json({error: 'book not found'});
            res.json(books);
        })
    });

    // CREATE BOOK
    app.post('/api/books', function(req, res){
        var book = new Book();
        book.title = req.body.title;
        book.author = req.body.author;
        book.published_date = new Date(req.body.published_date);

        book.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});

        });
    });

    // UPDATE THE BOOK
    app.put('/api/books/:book_id', function(req, res){
        Book.update({ _id: req.params.book_id }, { $set: req.body }, function(err, output){
            if(err) res.status(500).json({ error: 'database failure' });
            console.log(output);
            if(!output.n) return res.status(404).json({ error: 'book not found' });
            res.json( { message: 'book updated' } );
        })
    /* [ ANOTHER WAY TO UPDATE THE BOOK ]
            Book.findById(req.params.book_id, function(err, book){
            if(err) return res.status(500).json({ error: 'database failure' });
            if(!book) return res.status(404).json({ error: 'book not found' });
            if(req.body.title) book.title = req.body.title;
            if(req.body.author) book.author = req.body.author;
            if(req.body.published_date) book.published_date = req.body.published_date;
            book.save(function(err){
                if(err) res.status(500).json({error: 'failed to update'});
                res.json({message: 'book updated'});
            });
        });
    */
    });

    // DELETE BOOK
    app.delete('/api/books/:book_id', function(req, res){
        Book.remove({ _id: req.params.book_id }, function(err, output){
            if(err) return res.status(500).json({ error: "database failure" });

            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "book not found" });
            res.json({ message: "book deleted" });
            */

            res.status(204).end();
        })
    });

}
