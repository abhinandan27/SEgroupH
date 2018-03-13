#install.packages('ISLR')
library(ISLR)
#install.packages('neuralnet')
library(neuralnet)

data<-read.csv("C:/NCSU/SE/neural/update/user2-brd.csv",header=TRUE,sep = ",")
#data<-read.csv("C:/NCSU/SE/neural/milk.csv",header=TRUE,sep = ",")
#View(data)
str(data)
#fdata<-as.data.frame(data)
#View(fdata)
week = as.numeric(data$weekofmonth)
workload = as.numeric(data$workload)
people = as.numeric(data$noofpeople)
season = as.numeric(data$season)
holidays = as.numeric(data$holidays)
output = as.numeric(data$outputdays)
product = as.data.frame(data$product)
#View(week)
#View(workload)
#View(people)
#View(season)
#View(holidays)
#View(output)
#View(product)
ndata = cbind(workload,week,people,season,holidays,output,product)
maxv<-as.numeric(apply(ndata[,1:5], 2, max))
minv<-as.numeric(apply(ndata[,1:5], 2, min))
#View(maxv)
#View(minv)
sdata <- as.data.frame(scale(ndata[,1:5],center = minv, scale = maxv - minv+0.0001))
#View(sdata)

updata = cbind(sdata,output,product)

library(caTools)
set.seed(16)
split = sample.split(updata$output, SplitRatio = 0.80)

ntrain = subset(updata, split == TRUE)
ntest = subset(updata, split == FALSE)

feats <- names(sdata)

f <- paste(feats,collapse=' + ')
f <- paste('output ~',f)

f <- as.formula(f)

f

nn <- neuralnet(f,ntrain,hidden=c(10,10,10),linear.output=TRUE)

predicted.nn.values <- compute(nn,ntest[1:5])

predicted.nn.values$net.result <- sapply(predicted.nn.values$net.result,round,digits=0)

print(predicted.nn.values$net.result)

finalresult = cbind(ntest,predicted.nn.values$net.result)

write.csv(finalresult, file = "user2-re-brd.csv")

#table(ntest$output,predicted.nn.values$net.result)
