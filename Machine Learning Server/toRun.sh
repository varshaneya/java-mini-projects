javac ./ML/SendFile2Server.java
javac ./ML/ReceiveFileFromClient.java
javac -cp .:ajt.jar:jama.jar:javaml.jar ./ML/KNNClassifier.java
javac -cp .:ajt.jar:jama.jar:javaml.jar ./ML/RandomForestClassifier.java
javac Client.java
javac Server.java
javac ClientMain.java
javac ServerMain.java