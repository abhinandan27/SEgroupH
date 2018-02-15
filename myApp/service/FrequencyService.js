var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var Lru=require('../service/LRUService');
var db = DBService.db;

exports.update = function(emailId,list,date) {
    
    var newDate=datelib.parse(date,"YYYYMMDD");
    var frequency_collection = db.get('frequency_collection');
    var query={"users.emailId":emailId};

    frequency_collection.find(query, {},function(e,results){
        if(results.length==0)
        {
            var itemArr=list.filter(function(elem, pos) {
                return list.indexOf(elem) == pos; });
            users={}
            users.emailId=emailId;
            users.items=[];
            
            dates=[]
            dates.push(datelib.parse(date,"YYYYMMDD"));
            for (var i = itemArr.length - 1; i >= 0; i--) {
                var frequeny={};
                frequeny.item=itemArr[i];
                frequeny.dates=dates;
                users.items.push(frequeny);
            }

            
            frequency_collection.insert({
                users,
            }, function (err, result) {});

        }
        else
        {

        	var itemArr=list.filter(function(elem, pos) {
                return list.indexOf(elem) == pos; });


            var itemsList=results[0].users.items;
            var itemsNameList=[]
            var newItemsList=[];
            for (var i =0;i<itemsList.length ; i++) {
                itemsNameList.push(itemsList[i].item);
            }
            

            for (var i =itemsNameList.length - 1; i >= 0; i--) {
                if(list.indexOf(itemsNameList[i])>0)
                {
                    var frequeny={};
                    frequeny.item=itemsList[i].item;
                    frequeny.cycle=itemsList[i].cycle;
                    newItemsList.push(frequeny);
                }
            }
            

            for (var i =list .length - 1; i >= 0; i--) {
                if(itemsNameList.indexOf(list[i])<0)
                {
                    var frequeny={};
                    frequeny.item=list[i];
                    frequeny.cycle=0;
                    newItemsList.push(frequeny);
                }
            }

            frequency_collection.update(
                { "users.emailId" : emailId , },
                    { $set: { "users.items": newItemsList } },
                    function(err, results) {
                        console.log("Frequency Update done:"+results);
                });

        }
        Lru.update(emailId,list,date);
    });    
};
