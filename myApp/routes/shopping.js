var express = require('express');
var router = express.Router();
var User=require('../model/User');
var Shopping=require('../model/Shopping');
var Frequeny=require('../service/FrequencyService');
var Lru= require('../service/LRUService');
var datelib = require('date-and-time');

var analysisService=require('../service/AnalysisService')


router.post('/addItem', function(req, res) {
	var db = req.db;
    var emailId = req.body.emailId;
    var date=req.body.date;
    var workload=req.body.workload;
    var number_of_people=req.body.number_of_people;
    var season=req.body.season;
    var week_of_month=req.body.week_of_month;
    var holidays=req.body.holidays;
    var list=req.body.list;
    var items=req.body.list;

    list=JSON.parse(list);

    var shopping_collection = db.get('shopping_collection');
    var query={"shopping.emailId":emailId};
    //Check if email ID already exists
    shopping_collection.find(query, {},function(e,results){
        
        var shoppingdata={}
        shoppingdata.date=date;
        shoppingdata.workload=workload;
        shoppingdata.number_of_people=number_of_people;
        shoppingdata.season=season;
        shoppingdata.week_of_month=week_of_month;
        shoppingdata.holidays=holidays;
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
                            //console.log("Update done:"+results);
                        });


            var shoppingData=results.data;
            //console.log(results);

        }

	});
    
    for(var itemNo =0;itemNo<list.length;itemNo++)
    {
        //console.log("hello")
        Frequeny.update(emailId,list[itemNo],date,workload,number_of_people,season,week_of_month,holidays);
    }


    res.send("Done");
});


router.get('/getList', function(req, res) {
    var emailId=req.query.emailId;
    console.log(emailId);
    var dateString=req.query.date;
    var date=datelib.parse(dateString,"YYYYMMDD");
    var db = req.db;
    var end_date_collection = db.get('end_date_collection');
    var query={"users.emailId":emailId};
    end_date_collection.find(query, {},function(e,results){
        if(results.length>0)
        {
            var items=[]

            for (var i = 0; i < results[0].users.items.length; i++) {
                console.log(results[0].users.items.lru + " "+ date);
                if(datelib.subtract(results[0].users.items[i].lru, date).toHours() <0  )
                {
                    items.push(results[0].users.items[i].item);
                }
            }
            res.send(items);
        }   
        else
        {
            res.send("No data found");
        }
    });
});


module.exports = router;

