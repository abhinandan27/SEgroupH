var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var Lru=require('../service/LRUService');
var analysisService=require('../service/AnalysisService')
var db = DBService.db;

exports.update = function(emailId,item,date,workload,number_of_people,season,week_of_month,holidays) {
    
    

    var newDate=datelib.parse(date,"YYYYMMDD");
    var frequency_collection = db.get('frequency_collection');
    var lru_collection=db.get('lru_collection');
    var query={"users.emailId":emailId};

    
    //console.log(item);
    var frequency_collection = db.get('frequency_collection');
    var query1={"users.emailId":emailId};
    frequency_collection.find(query1, {},function(e,results)
    {
        if(results.length==0)
        {
            //console.log("A");
            var shoppingdata={}
            shoppingdata.date=datelib.parse(date,"YYYYMMDD");
            shoppingdata.workload=workload;
            shoppingdata.number_of_people=number_of_people;
            shoppingdata.season=season;
            shoppingdata.week_of_month=week_of_month;
            shoppingdata.holidays=holidays;
            shoppingdata.frequency=-1;
            users={}
            users.emailId=emailId;
            users.items=[];
            
            dates=[]
            dates.push(shoppingdata);
            var firstItem={}
            firstItem.item=item
            firstItem.dates=dates
            users.items.push(firstItem)

            frequency_collection.insert({users}, function (err, result) {
                //console.log("Added");
                analysisService.updateItem(emailId,item,date,workload,number_of_people,season,week_of_month,holidays);
                //console.log(item);
            });

        }
        else
        {
            //console.log("B");
            //console.log("FS: User present!");
            var frequency_object=results[0]
            var frequency_object_itemList=frequency_object.users.items
            items=[]
            var found=false;
            var item_already_present;
            var shoppingdata={}
            shoppingdata.date=datelib.parse(date,"YYYYMMDD");
            shoppingdata.workload=workload;
            shoppingdata.number_of_people=number_of_people;
            shoppingdata.season=season;
            shoppingdata.week_of_month=week_of_month;
            shoppingdata.holidays=holidays;
            shoppingdata.frequency=-1;
            var query2={"users.emailId":emailId,"users.items.item":item};
            lru_collection.find(query2, {},function(e,newresults){
                

                if(newresults.length>0)
                {
                    //console.log("C");
                    var olddate;
                    for(var i=0;i<newresults[0].users.items.length;i++)
                    {
                        if(newresults[0].users.items[i].item==item)
                            olddate=newresults[0].users.items[i].lru;
                    }
                    //console.log("LRU found");
                    for(var i=0;i<frequency_object_itemList.length;i++)
                    {
                        if(frequency_object_itemList[i].item==item)
                        {
                            item_already_present=frequency_object_itemList[i];
                            found=true;
                        }
                    }

                    shoppingdata.frequency=datelib.subtract(shoppingdata.date, olddate).toDays(); 


                    var new_item={}
                    
                    if(found)
                    {
                        new_item.item=item_already_present.item;
                        new_item.dates=item_already_present.dates;
                        new_item.dates.push(shoppingdata);
                        items.push(new_item);
                    }
                    


                    for(var i=0;i<frequency_object_itemList.length;i++)
                    {
                        if(frequency_object_itemList[i].item!=item)
                        {
                            items.push(frequency_object_itemList[i]);
                        }
                    }

                    

                }
                else
                {
                    //console.log("D");
                    //console.log("LRU not found");
                    var new_item={}
                    new_item.item=item;
                    new_item.dates=[];
                    new_item.dates.push(shoppingdata);

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
                        //console.log("Frequency Update done:"+results);
                        analysisService.updateItem(emailId,item,date,workload,number_of_people,season,week_of_month,holidays);
                });
            });
        }
    Lru.update(emailId,item,date);
    });    
} 

