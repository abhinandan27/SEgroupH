var express = require('express');
var router = express.Router();
var User=require('../model/User');
var Shopping=require('../model/Shopping');
var Frequeny=require('../service/FrequencyService');
var Lru= require('../service/LRUService');
var datelib = require('date-and-time');


router.post('/addItem', function(req, res) {
	var db = req.db;
    var emailId = req.body.emailId;
    var date=req.body.date;
    var list=req.body.list;


    var shopping_collection = db.get('shopping_collection');
    var query={"shopping.emailId":emailId};
    //Check if email ID already exists
    shopping_collection.find(query, {},function(e,results){
        
        var shoppingdata={}
        shoppingdata.date=date;
        shoppingdata.list=list;



        if(results.length==0)
        {
            var shopping= Shopping.create();
            shopping.emailId=emailId;
            var arr=[]
            arr.push(shoppingdata);
            shopping.data=arr;

            var shopping_collection1 = db.get('shopping_collection');
            shopping_collection1.insert({
                    shopping,
                }, function (err, result) {
                        
                });
        }
        else
        {
            //console.log(results);
            var alreadyList=[];
            var history= results[0].shopping.data;
            var newData=[]
            for (var i = results[0].shopping.data.length - 1; i >= 0; i--) {
                if(results[0].shopping.data[i].date==date)
                {
                    alreadyList=results[0].shopping.data[i].list;
                }
                else
                {
                    newData.push(results[0].shopping.data[i]);
                }
            }
            for(var i=0;i<alreadyList.length;i++)
            {
                shoppingdata.list.push(alreadyList[i]);
            }
            newData.push(shoppingdata);


            var shopping_collection1 = db.get('shopping_collection');
            shopping_collection1.update(
                        { "shopping.emailId" : emailId , },
                        { $set: { "shopping.data": newData } },
                        function(err, results) {
                            console.log("Update done:"+results);
                        });


            var shoppingData=results.data;
            //console.log(results);
        }

        Frequeny.update(emailId,list,date);
        res.send("Done");
	});
});

module.exports = router;

