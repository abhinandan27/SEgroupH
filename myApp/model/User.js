function User(){
	this.name = null;
	this.emailId = null;
	this.password = null;
};

exports.create = function () {
    var instance = new User();    
    return instance;
};