To run Java file
compile : javac *.java

to run the program:
run: java Driver trainingData.arrf testData.arff

The output contains:
1.  Mean absolute error for J48
2.  Mean absolute error for NBC
3.  Prefer XXX algorithm
4.  Details for XXX algorithm
5.  Test Error.

Note:
1. use command line arguments for specifying the training data and test data files.
2. the J48 ( C4.5 decision tree algorithm ) need range for MinNumObj and NumFold to calculate the mean absolute error.

