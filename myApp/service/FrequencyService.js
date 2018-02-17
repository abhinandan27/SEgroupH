var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var Lru=require('../service/LRUService');
var db = DBService.db;

exports.update = function(emailId,list,date) {
    
    var newDate=datelib.parse(date,"YYYYMMDD");
    var frequency_collection = db.get('frequency_collection');
    var query={"users.emailId":emailId};

    
    console.log(list);
    var frequency_collection = db.get('frequency_collection');
    var query={"users.emailId":emailId};
    frequency_collection.find(query, {},function(e,results)
    {
        if(results.length==0)
        {
            console.log("New User!");
            users={}
            users.emailId=emailId;
            users.items=[];
            
            dates=[]
            dates.push(datelib.parse(date,"YYYYMMDD"));
            var firstItem={}
            firstItem.item=list
            firstItem.dates=dates
            users.items.push(firstItem)

            frequency_collection.insert({users}, function (err, result) {
                console.log("Added");
                console.log(list);});

        }
        else
        {
            console.log("User present!");
            var frequency_object=results[0]
            var frequency_object_itemList=frequency_object.users.items
            items=[]
            var found=false;
            var item_already_present;


            for(var i=0;i<frequency_object_itemList.length;i++)
            {
                if(frequency_object_itemList[i].item==list)
                {
                    item_already_present=frequency_object_itemList[i];
                    found=true;
                    break; 
                }
            }

            if(found)
            {
                console.log("Item found!");
                var new_item={}
                new_item.item=item_already_present.item;
                new_item.dates=item_already_present.dates;
                new_item.dates.push(datelib.parse(date,"YYYYMMDD"));


                for(var i=0;i<frequency_object_itemList.length;i++)
                {
                    if(frequency_object_itemList[i].item!=list)
                    {
                        items.push(frequency_object_itemList[i]);
                    }
                }

                items.push(new_item);
            }
            else
            {
                console.log("Item not found!");
                var new_item={}
                new_item.item=list;
                new_item.dates=[];
                new_item.dates.push(datelib.parse(date,"YYYYMMDD"));

                for(var i=0;i<frequency_object_itemList.length;i++)
                {
                    items.push(frequency_object_itemList[i]);
                }

                items.push(new_item);
            }

        frequency_collection.update(
            { "users.emailId" : emailId , },
                { $set: { "users.items": items } },
                function(err, results) {
                    console.log("Frequency Update done:"+results);
            });


        }

    });    
    // for(var itemNo =0;itemNo<list.length;itemNo++)
    // {
    //     Lru.update(emailId,list[itemNo],date);
    // }
} 

