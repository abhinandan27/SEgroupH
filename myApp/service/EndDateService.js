var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var db = DBService.db;

exports.update = function(emailId,item,date) {
    console.log("End date service")
	var end_date_collection = db.get('end_date_collection');
    var query={"users.emailId":emailId};

    end_date_collection.find(query, {},function(e,results){
        if(results.length==0)
        {
            //console.log("New User");
            //console.log(item);
            users={}
            users.emailId=emailId;
            users.items=[];
            var frequeny={};
            frequeny.item=item;
            frequeny.lru=date;
            users.items.push(frequeny);
            
            end_date_collection.insert({ 
                users,
            }, function (err, result) {});

        }
        else
        {
            //console.log("User Found");
            //console.log(item);
            alreadyList=[]
            itemsNameList=[]

            var itemsList=results[0].users.items;
            for (var i =0;i<itemsList.length ; i++) {
                itemsNameList.push(itemsList[i].item);
            }
            var newItemList=[]
            var found=false;

            for (var j = itemsNameList.length - 1; j >= 0; j--) {

                if(itemsNameList[j]==item)
                {
                    found=true;
                    var frequeny={};
                    frequeny.item=itemsList[j].item;
                    //console.log(frequeny.item);
                    frequeny.lru= date;
                    newItemList.push(frequeny);
                }
                else
                {
                    //console.log("Item not Present");
                    var frequeny={};
                    frequeny.item=itemsList[j].item;
                    frequeny.lru= itemsList[j].lru;
                    newItemList.push(frequeny);
                }
            }

            if(!found)
            {
                //console.log("Item Present");
                var frequeny={};
                frequeny.item=item;
                frequeny.lru= date;
                newItemList.push(frequeny);
            }
            

            end_date_collection.update(
                { "users.emailId" : emailId , },
                    { $set: { "users.items": newItemList } },
                    function(err, results) {
                        //console.log("Update done:"+results);
                });

        }
    });
  
};