# SEgroupH
This repository contains project related data of Spring SE 2018 group H
## Smart Shopping List
We often buy groceries and stuff from grocery markets. Sometimes we make a list of items we need to buy and sometimes we don’t. Even if we make a list of items, there is a chance that we may forget some important things. This results in another trip to grocery store which cost us our money and time. To overcome this problem, we have developed a smart shopping application which makes a list of items you need to buy based on previous history of shopping. User enters items he bought in the previous visits and get a list of items which may finish soon or already finished.

### Instructions 
#### Prerequisites:
To run the server side code, you need to have Nodejs and MongoDB installed on your system.
#### Installing NodeJs
1)	Download Nodejs from https://nodejs.org/en/
2)	Open a downloaded file and run it.
3)	Follow the instructions in prompt.
4)	After successful installation, restart your computer.
#### Installing MongoDB
1)	Download mongoDB from https://docs.mongodb.com/tutorials/install-mongodb-on-windows/
2)	Open downloaded file and run it.
3)	Follow the instructions in prompt
4)	After successful installation, make a new folder where you want to store your data
To specify the exact path for your folder , use the following command.
"C:\Program Files\MongoDB\Server\3.4\bin/mongod.exe" --dbpath C:\data\db
Here C:\data is the data folder which we have created.
5)	Go to mongoDB installation directory in your computer.
For ex.  C:\Program Files\MongoDB\Server\3.4\bin
Open command prompt here and type mongod and press enter.
You will see a mongo shell where you can see all your databases using command show dbs.
Type a command use process
process is a name of our database.
For more information about mongo shell, refer the following link.
https://docs.mongodb.com/manual/reference/mongo-shell/

#### Running the server
Once you are done with NodeJs and MongoDB installation, download a our code repository from https://github.com/abhinandan27/SEgroupH
-	Go to myApp and open command prompt in the same directory.
-	There are several dependencies like Express.js and brain.js which we need to install before we run our server. All those dependencies are mentioned in package.json file in myApp directory. To install those dependencies, run a command npm init
This will install all the dependencies.
-	Run command npm start
-	Now we are done with server installation

#### Running the application:
To run this application, download android studio from https://developer.android.com/studio/index.html
and install android studio.
-	Open android studio and go to File->Open project
-	Choose a directory where you Smart list exists.
-	Once you open a project dependencies will be synced
-	Open Server.java file as shown below and change the server address.
-	Once server address is change, install application on your mobile and use it.

#### Flow of Application:
![alt text](https://github.com/abhinandan27/SEgroupH/blob/master/Android.jpg)
-	User is asked to login. If he does not have an account, then he can sign up.
-	After signing in, user gets a survey page where he needs to fill the information about holidays, workload and number of people etc.
-	After this, user can upload items on the server or he can see a list of predicted items.

#### Flow of adding items on the server side
![alt text](https://github.com/abhinandan27/SEgroupH/blob/master/Server1.jpg)

#### Chits
- XKQ
- QLI
- XIO
- HLL
- BSH
- YFD
- XGY
- HVE
- OBO
- QZW
- RHQ
- BQU
- MXI
- DGP
- LGS
- JBT
- WJL
- YDK
- MIH
- NPB

 
