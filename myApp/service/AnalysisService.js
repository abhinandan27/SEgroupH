var DBService = require('../service/DBService');
var EndDateService = require('../service/EndDateService');
var datelib= require('date-and-time');
var db = DBService.db;
var Net=require('../model/Net')
var brain=require('brain.js');
var AnalysisService=require('../service/AnalysisService');

/*

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
*/


exports.updateNN = function(emailId,item,date,workload,number_of_people,season,week_of_month,holidays) {
    //console.log("ANalysis");
    date=datelib.parse(date,"YYYYMMDD");
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
                    if(element.frequency==-1)
                    {
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
                    
                }

                //console.log(data);
                //console.log(result);

                
                // this line is not needed in the browser 
                var net = new brain.NeuralNetwork({
                      activation: 'tanh', // activation function
                      hiddenLayers: [3, 3],
                      learningRate: 0.4 // global learning rate, useful when training using streams
                    });
 
                
                HardCodeInput=[
                                {  input: [ 0, 1, 0, 0, 1, 0 ],output: [ 1 ] },
                                {  input: [ 0, 1, 0, 0, 1, 0 ],output: [ 1 ] },
                                {  input: [ 0, 1, 0, 0.25, 1, 0 ],output: [ 1 ] },
                                {  input: [ 0.5, 1, 0, 1, 0, 0 ],output: [ 0.5 ] },
                                {  input: [ 0.5, 1, 1, 0, 0, 0 ],output: [ 0.5 ] },
                                {  input: [ 0.5, 0, 1, 0, 0, 0 ],output: [ 0.5 ] },
                                {  input: [ 0.5, 0, 1, 0.5, 0, 0 ],output: [ 0 ] },
                                {  input: [ 1, 0, 1, 0.75, 0, 0 ],output: [ 0 ] },
                                {  input: [ 1, 0, 1, 0.75, 0, 0 ],output: [ 1 ] }

                            ];

                net.trainAsync(HardCodeInput,
                    {
                            // Defaults values --> expected validation
                          iterations: 50000,    // the maximum times to iterate the training data --> number greater than 0
                          errorThresh: 0.05,   // the acceptable error percentage from training data --> number between 0 and 1
                          log: true,           // true to use console.log, when a function is supplied it is used --> Either true or a function
                          logPeriod: 1000,        // iterations between logging out --> number greater than 0
                          learningRate: 0.4,    // scales with delta to effect traiing rate --> number between 0 and 1
                          momentum: 0.2,        // scales with next layer's change value --> number between 0 and 1
                          callback: null,       // a periodic call back that can be triggered while training --> null or function
                          callbackPeriod: 10,   // the number of iterations through the training data between callback calls --> number greater than 0
                          timeout: 500000     // the max number of milliseconds to train for --> number greater than 0
                    }


                ).then(res => {
                  // do something with my trained network

                  var netObject=Net.create();
                  netObject.emailId=emailId;
                  netObject.item=item;
                  netObject.net=net.toJSON();
                  var output=net.run([ 0.5, 0, 1, 0, 0, 0 ]); 
                  console.log("OPin: "+output);
                  //console.log(net.toJSON());
                 


                  var net_collection = db.get('net_collection');
                            net_collection.insert({
                            netObject,
                        }, function (err, result) {
                            console.log("Net inserted"+results)
                        });
                }).catch();
                
                //console.log(net.toJSON());
                //var output =net.run([workload, number_of_people, season, week_of_month, holidays, item]);  // [1]

                //EndDateService.update(emailId,item,datelib.addDays(date, output));

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

exports.updateItem = function(emailId,item,dateString,workload,number_of_people,season,week_of_month,holidays) {
    date=datelib.parse(dateString,"YYYYMMDD");
    var net_collection=db.get('net_collection');
    var query={"netObject.emailId":emailId,"netObject.item":item};
    var data=[];
    var result=[];
    net_collection.find(query, {},function(e,results)
    {
        if(results.length>0)
        {
            console.log("Net found")
            var net = new brain.NeuralNetwork({
              activation: 'sigmoid', // activation function
              hiddenLayers: [4],
              learningRate: 0.6 // global learning rate, useful when training using streams
            });
            //console.log("Hello"+results[0].netObject.net)
            net.fromJSON(results[0].netObject.net);
            var output=net.run([0, 1, 0, 0, 1, 0 ]); 
            console.log("OP: "+output);
            
            if(output!="NaN")
                EndDateService.update(emailId,item,datelib.addDays(date, output));
            AnalysisService.updateNN(emailId,item,dateString,workload,number_of_people,season,week_of_month,holidays);
        }
        else
        {
            console.log("No net found")
            AnalysisService.updateNN(emailId,item,dateString,workload,number_of_people,season,week_of_month,holidays);
        }
    });
};























