function Shopping(){
	this.emailId = null;
	this.data= null;
};

exports.create = function () {
    var instance = new Shopping();    
    return instance;
};