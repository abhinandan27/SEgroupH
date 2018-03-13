var express = require('express');
var router = express.Router();
var mongo = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";
var User=require('../model/User');

/* GET home page. */
router.post('/addUser', function(req, res) {
	var db = req.db;
	var user=User.create();
	user.name = req.body.name;
    user.emailId = req.body.emailId;
    user.password = req.body.password;

    
    var collection = db.get('user_collection');
    
    //Check if email ID already exists
    collection.find({ "user.emailId" : user.emailId }, {}, function(e,results){
        
        if(results.length !== 0)
            res.status(401).send({ "message" : "Emaild ID already in use"});
        else{
            collection.insert({
                user,
            }, function (err, result) {
                if (err) {
                    res.status(500).send("There was a problem adding User to the database.");
                }
                else {
                    var userData = result;
                    res.status(201).send("Success");
                }
            });
        }
    });
    

});

//prashant
router.get('/login', function(req, res) {
	var db = req.db;
	//var user=User.create();
	
    var emailId = req.query.emailId;
    var password = req.query.password;

    
    
    var collection = db.get('user_collection');
    
    //Check if email ID already exists
    collection.find({ "user.emailId" : emailId }, {}, function(e,results){
        
        if(results.length !== 0)
            {
                //console.log(password+"   "+results[0].user.password);
                if(password==results[0].user.password)
                    res.status(201).send("Allow login");
                    else{
                        res.status(401).send("Invalid Password");
                 }
            }
        else
        {
            res.status(401).send("User not found");
        }
    });
    

});


module.exports = router;

