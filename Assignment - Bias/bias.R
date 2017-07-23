#input x \epsilon 0,5 with normal distribution (0,1)
x<-seq(0,5, length.out = 20)

#y is cos function
y<-(2*cos(2.8*x)+7.5)
z<-(x+y)
#add noise to the cos fucntion.
noise<-rnorm(x,mean = 0, sd = 1)
input<-(y+noise)

#plot cos function with noise.
plot(x,y,main = " Function for cos", type='o' , ylab = "Y-axis", xlab = "X-axis",col = "green" ,pch = 20)
points(x,input, pch =20)

inputmatrix<-vector(mode="list", length=101)

for(i in 1:100){
  #inputmatrix[[i]]<-seq(0,5, length.out = 20)
  inputmatrix[[i]]<-x+y+rnorm(x,mean = 0,sd = 1)
 
}
average<-do.call(rbind,inputmatrix)
averageFits<-colMeans(average)
#inputmatrix ready!

#set1

plot(x,inputmatrix[[i]], main = "1st fit", type = 'n', ylab = "Y-axis", xlab = "X-axis", col= "red", pch=20)

for(i in 1:5){
  m1<-lm( inputmatrix[[i]] ~ x )
  
  lines.default(x,predict(m1),type ='l', col=i)
  m1<-lm(averageFits ~ x)
  lines.default(x,predict(m1), type = 'l', col=6, lty=3)
  
}

#set2
plot(x,inputmatrix[[i]], main = "2nd fit", type = 'n', ylab = "Y-axis", xlab = "X-axis", col= "green", pch=20)

for(i in 1:5){
  m1<-lm(inputmatrix[[i]]~ x + I(x^2) )
  #points(x,predict(m1), pch = 1)
  lines.default(x,predict(m1),type ='l', col=i)
  m1<-lm(averageFits ~ x + I(x^2))
  lines.default(x,predict(m1), type = 'l', col=6, lty=3)
}


#set3
plot(x,inputmatrix[[i]], main = "3rd fit", type = 'n', ylab = "Y-axis", xlab = "X-axis", col= "blue", pch=20)

for(i in 1:5){
  m1<-lm(inputmatrix[[i]] ~ x + I(x^2) + I(x^3) )
  #points(x,predict(m1), pch = 1)
  lines.default(x,predict(m1),type ='l', col=i)
  m1<-lm(averageFits ~ x + I(x^2) + I(x^3))
  lines.default(x,predict(m1), type = 'l', col=6, lty=3)
}

#set4
plot(x,inputmatrix[[i]], main = "4th fit", type = 'n', ylab = "Y-axis", xlab = "X-axis", col= "black", pch=20)

for(i in 1:5){
  m1<-lm( inputmatrix[[i]]~ x + I(x^2) + I(x^3) + I(x^4))
  
  #points(x,predict(m1), pch = 1)
  lines.default(x,predict(m1),type ='l', col=i)
  m1<-lm(averageFits ~ x + I(x^2) + I(x^3) + I(x^4))
  lines.default(x,predict(m1), type = 'l', col=6, lty=3)
}


#set5
plot(x,inputmatrix[[i]], main = "5th fit", type = 'n', ylab = "Y-axis", xlab = "X-axis", col= "red", pch=20)

for(i in 1:5){
  m1<-lm( inputmatrix[[i]]~ x + I(x^2) + I(x^3) + I(x^4) + I(x^5))
  #points(x,predict(m1), pch = 1)
  lines.default(x,predict(m1),type ='l', col=i)
  m1<-lm(averageFits ~ x + I(x^2) + I(x^3) + I(x^4) + I(x^5))
  lines.default(x,predict(m1), type = 'l', col=6, lty=3)
}

#------------Part A completes.
#------------Average plotting.