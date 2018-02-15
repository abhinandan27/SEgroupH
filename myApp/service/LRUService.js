var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var db = DBService.db;

exports.update = function(emailId,list,date) {

	var lru_collection = db.get('lru_collection');
        var query={"users.emailId":emailId};

        lru_collection.find(query, {},function(e,results){
            if(results.length==0)
            {
                var itemArr=list.filter(function(elem, pos) {
                    return list.indexOf(elem) == pos; });
                users={}
                users.emailId=emailId;
                users.items=[];
                

                for (var i = itemArr.length - 1; i >= 0; i--) {
                    var frequeny={};
                    frequeny.item=itemArr[i];
                    frequeny.lru=datelib.parse(date, 'YYYYMMDD'); 
                    users.items.push(frequeny);
                }

                
                lru_collection.insert({
                    users,
                }, function (err, result) {});

            }
            else
            {
                alreadyList=[]
                itemsNameList=[]
                var itemArr=list.filter(function(elem, pos) {
                    return list.indexOf(elem) == pos; });

                var itemsList=results[0].users.items;
                for (var i =0;i<itemsList.length ; i++) {
	                itemsNameList.push(itemsList[i].item);
	            }
                var newItemList=[]

                for (var j = itemsNameList.length - 1; j >= 0; j--) {

                    if(itemArr.indexOf(itemsNameList[j])>-1)
                    {
                        var frequeny={};
                        frequeny.item=itemsList[j].item;
                        console.log(frequeny.item);
                        frequeny.lru= datelib.parse(date, 'YYYYMMDD'); 
                        newItemList.push(frequeny);
                    }
                    else
                    {
                        var frequeny={};
                        frequeny.item=itemsList[j].item;
                        frequeny.lru= itemsList[j].lru;
                        newItemList.push(frequeny);
                    }
                }

                for (var j = itemArr.length - 1; j >= 0; j--) {

                    if(itemsNameList.indexOf(itemArr[j])<0)
                    {
                        var frequeny={};
                        frequeny.item=itemArr[j];
                        frequeny.lru= datelib.parse(date, 'YYYYMMDD'); 
                        newItemList.push(frequeny);
                    }
                }

                lru_collection.update(
                    { "users.emailId" : emailId , },
                        { $set: { "users.items": newItemList } },
                        function(err, results) {
                            console.log("Update done:"+results);
                    });

            }
        });
  
};