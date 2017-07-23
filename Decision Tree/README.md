Consider the training data set given in the Weather.pdf file.
The last column contains the values of the class label Play (whether to play tennis
or not), which is determined by the values of four features for weather
conditions: Outlook, Temperature, Humidity, and Windy. In C4.5 decision
tree algorithm, a tree is built by multiple-branch splitting based on Gain
Ratio as the uncertainty measure, defined as follows:

GainRatio(Y |X)= (H(Y ) - H(Y |X))/H(X),

where H(X) is the entropy of the splitting feature. Implement the Gain Ratio
measure and use it to decide the best feature for the first split of a C4.5
decision tree on the training data (the larger the Gain Ratio, the better the
feature). Program needs to output the Gain Ration value for each of the
three features.