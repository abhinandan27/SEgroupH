var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var db = DBService.db;
var brain=require('brain.js');

exports.update = function(emailId,item,date) {


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


exports.updateNN = function(emailId,item,date) {
    console.log("ANalysis");
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
                    
                        row.push(item);

                    var inputData={}
                    inputData.input=row
                    inputData.output=[]
                    inputData.output.push(element.frequency);
                    data.push(inputData);
                    if(element.frequency!=undefined)
                        result.push(element.frequency);
                    else
                        result.push(0);
                }

                console.log(data);
                //console.log(result);

                
                // this line is not needed in the browser 
                var net = new brain.recurrent.RNN();
 
                
                HardCodeInput=[
                                {  input: [ 'low', '4', 'summer', '4', 'yes', 'milk' ],output: [ 6 ] },
                                {  input: [ 'low', '4', 'summer', '4', 'yes', 'milk' ],output: [ 6 ] },
                                {  input: [ 'low', '4', 'summer', '3', 'yes', 'milk' ],output: [ 6 ] },
                                {  input: [ 'low', '4', 'summer', '3', 'yes', 'milk' ],output: [ 5 ] },
                                {  input: [ 'medium', '4', 'summer', '4', 'no', 'milk' ],output: [ 5 ] },
                                {  input: [ 'medium', '4', 'summer', '5', 'no', 'milk' ],output: [ 5 ] },
                                {  input: [ 'medium', '4', 'fall', '', 'no', 'milk' ],output: [ 5 ] },
                                {  input: [ 'medium', '4', 'fall', '2', 'no', 'milk' ],output: [ 5 ] },
                                {  input: [ 'medium', '4', 'fall', '4', 'no', 'milk' ],output: [ 5 ] },
                                {  input: [ 'medium', '4', 'fall', '3', 'no', 'milk' ],output: [ 5 ] },
                                {  input: [ 'medium', '4', 'fall', '3', 'no', 'milk' ],output: [ 4 ] },
                                {  input: [ 'heavy', '4', 'fall', '4', 'no', 'milk' ],output: [ 4 ] },
                                {  input: [ 'heavy', '4', 'fall', '1', 'no', 'milk' ],output: [ 4 ] },
                                {  input: [ 'low', '4', 'fall', '5', 'yes', 'milk' ],output: [ 100 ] },
                                {  input: [ 'low', '4', 'fall', '2', 'no', 'milk' ],output: [ 6 ] },
                                {  input: [ 'medium', '4', 'fall', '2', 'no', 'milk' ],output: [ 5 ] }

                                ];

                net.train(HardCodeInput);
                var output =net.run(['medium', '4', 'fall', '3', 'yes', 'milk']);  // [1]

                console.log(output);

            }
            else
            {
                console.log("Nothing to sh!");
                var net = new brain.recurrent.RNN();
 
                net.train([{input: [0, 0], output: [0]},
                           {input: [0, 1], output: [1]},
                           {input: [1, 0], output: [1]},
                           {input: [1, 1], output: [0]}]);
                 
                var output = net.run([0, 0]); 

                console.log(output);
            }
        }
        else
        {
            console.log("Nothing to show!");
            var net = new brain.recurrent.RNN();
 
                net.train([{input: [0, 0], output: [0]},
                           {input: [0, 1], output: [1]},
                           {input: [1, 0], output: [1]},
                           {input: [1, 1], output: [0]}]);
                 
                var output = net.run([0, 0]);

                console.log(output);
        }
           
    });
    
};






















