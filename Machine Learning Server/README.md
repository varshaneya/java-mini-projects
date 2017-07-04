# machine-learning-java
This is a client-sever program where the server acts as a provider of machine learning services. The server program is multi threaded. 
Client can choose between K-nearest neighbour classifier and random forest classifier. These are used from the JavaML library. Once the client has chosen the type of classifier, it uploads the training dataset to the server using the networking facility in JAVA.
The server runs a thread for a client where the classifier is trained with the dataset provided by the client. The client is then asked to enter a test data for prediction by the model trained in the server.
If you wish to compile the program again run the toRun.sh file  in linux distros after giving the necessary exceute permissions.

# Citiations
Abeel, T.; de Peer, Y. V. & Saeys, Y. Java-ML: A Machine Learning Library, Journal of Machine Learning Research, 2009, 10, 931-934
