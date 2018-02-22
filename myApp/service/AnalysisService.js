var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var db = DBService.db;
var ml = require('machine_learning');

exports.update = function(emailId,item) {

    var frequency_collection=db.get('frequency_collection');
    var query={"users.emailId":emailId};
    var data=[];
    var result=[];
    frequency_collection.find(query, {},function(e,results)
    {
        if(results.length>0)
        {
            //console.log("User found in ANalysis service: ",item);
            var datesArray=null;
            for(var i=0;i<results[0].users.items.length;i++)
            {
                if(results[0].users.items[i].item==item)
                    datesArray=results[0].users.items[i].dates;
            }
            if(datesArray!=null)
            {
                
                for(var i=0;i<datesArray.length;i++)
                {
                    var row=[];
                    //console.log(datesArray[i].frequency);
                    var element=datesArray[i];
                    if(element.workload!=null)
                        row.push(element.workload);
                    else
                        row.push('null');
                    
                    if(element.number_of_people!=null)
                        row.push(element.number_of_people);
                    else
                        row.push('null');
                    
                    if(element.season!=null)
                        row.push(element.season);
                    else
                        row.push('null');
                    
                    if(element.week_of_month!=null)
                        row.push(element.week_of_month);
                    else
                        row.push('null');
                    
                    if(element.holidays!=null)
                        row.push(element.holidays);
                    else
                        row.push('null');
                    
                    if(element.item!=null)
                    row.push(item);
                    else
                        row.push('null');
                    
                    data.push(row);
                    if(element.frequency!=undefined)
                        result.push(element.frequency);
                    else
                        result.push(0);
                }

                console.log(data);
                console.log(result);

                var dt = new ml.DecisionTree({data : data,result : result});

                dt.build();

                dt.print();
                
                console.log("Classify : ", dt.classify(['high','null','summer','null','null','null']));

            }
            else
            {
                console.log("Nothing to sh!");
            }
        }
        else
        {
            console.log("Nothing to show!");
        }
           
    });


    

    

};