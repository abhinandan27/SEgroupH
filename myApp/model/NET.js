function Net(){
	this.emailId = null;
	this.item= null;
	this.net=null;
};

exports.create = function () {
    var instance = new Net();    
    return instance;
};