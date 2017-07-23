#input x \epsilon 0,5 with normal distribution (0,1)
x<-seq(0,5, length.out = 20)

#y is cos function
y<-(2*cos(2.8*x)+7.5)

#add noise to the cos fucntion.
noise<-rnorm(x,mean = 0, sd = 1)
inputmatrix<-(y+noise)

#plot curve and points for cos function with noise.
plot(x,y,main = " Function for cos", type='l' , ylab = "Y-axis", xlab = "X-axis",col = "green", pch = 20 )
points(x,inputmatrix, pch =20)
lines(x,y)