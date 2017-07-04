Sairam!!!

Welcome to data mining server which offers K-Nearest neighbours and Random forest classifiers as services.

The java files are complied before they are zipped and if you are using a linux distro and want to compile once more then run assign2.sh on your machine. This contains commands for compilation of all the java files required.

Services offered by this server program are:
1) Receiving dataset and user input from the client over the network.
2) Random forest classifier.
3) K-nearest neighbour classifier.

Services offered by client program are:
1) Uploading dataset to the server over the network.
2) Appropriate exception handling.
3) Sending user input data to the server for building classifier and testing the new instance and also for menu driven program over the network.

Please include jar file jama.jar, ajt.jar and javaml.jar in your class path while running the server program. The classifiers in data mining server require them. The server program to be run is ServerMain.java which contains the main() method.

The client program to be run is ClientMain.java which contains the main() method. While running client program please pass the server IP address as the default arguement. If you dont, the program will prompt to give once it starts.

A dataset is included in the folder "sampleDataset" for the user to try the classifiers.

The classes for datamining routines are in the package ML.

Good theoritical knowledge about these classifiers can be found at:

https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm
https://en.wikipedia.org/wiki/Random_forest

Note: Some terms used in the data mining parlance are also used in java. So here are some clarifications on the terms that are used in data mining parlance.
1) class: The category to which a test sample belongs to.
2) instance: Instance is a tuple belonging to the dataset or it could be a test sample.

Credits for developing javaml package goes to Thomas Abeel.
