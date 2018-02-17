var DBService = require('../service/DBService');
var datelib= require('date-and-time');
var db = DBService.db;
var ml = require('machine_learning');

exports.update = function(emailId,item,date) {

    var data = [['slashdot','USA','yes',18],
            ['google','France','yes',23],
            ['digg','USA','yes',24],
            ['kiwitobes','France','yes',23],
            ['google','UK','no',21],
            ['(direct)','New Zealand','no',12],
            ['(direct)','UK','no',21],
            ['google','USA','no',24],
            ['slashdot','France','yes',19],
            ['digg','USA','no',18,],
            ['google','UK','no',18,],
            ['kiwitobes','UK','no',19],
            ['digg','New Zealand','yes',12],
            ['slashdot','UK','no',21],
            ['google','UK','yes',18],
            ['kiwitobes','France','yes',19]];

    var result = ['None','Premium','Basic','Basic','Premium','None','Basic','Premium','None','None','None','None','Basic','None','Basic','Basic'];
  
    var dt = new ml.DecisionTree({
        data : data,
        result : result
    });

    dt.build();

    dt.print();

    console.log("Classify : ", dt.classify(['(direct)','USA','yes',5]));



};