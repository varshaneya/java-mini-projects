/**
This class supports multi-threading and caters to the needs of the client by invoking the appropriate function.
The functionalities offered are:
* Transfer of dataset
* KNN classifier
* random Forest classifier
@author varshaneya
*/

import ML.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Server extends Thread
{
	/**
	private members of the class
	*/
	
	private PrintWriter message2Client = null;
	private BufferedReader messageFrmClient = null;
	private ReceiveFileFromClient fileFrmClient = null;
	private String datasetName = null;
	private String fullPath = null;
	private Socket myClientSocket = null;
	private boolean isCSV;
	private String delimitter;
	private int noOfAttrib;
	
	/**
	Constructor of the class
	*/
	
	public Server(Socket clientSoc) throws IOException,UnknownHostException
	{
		myClientSocket = clientSoc;
	}
	
	/**
	sends messages to the client that are generated as a part of the program
	*/
	
	
	private void sendMessage2Client(String message)
	{
		message2Client.println(message);
		message2Client.flush();
	}
	
	/**
	receives messages from the client that are sent.
	*/
	
	
	private String receiveMessageFromClient() throws IOException
	{
		return messageFrmClient.readLine();
	}
	
	/**
	Takes care of receiving the dataset from the client and calls receiveFrmClient function in class ReceiveFileFromClient
	*/
	
	private void requestTransferDataset() throws IOException
	{
		sendMessage2Client("Enter full name of the dataset to be sent to the server");
		datasetName = receiveMessageFromClient();
		
		sendMessage2Client("Enter full path of the dataset to be sent to the server");
		fullPath = receiveMessageFromClient();
		
		fileFrmClient.receiveFrmClient(myClientSocket,datasetName);
	}
	
	/**
	Checks if the uploaded dataset is a .CSV file so that the default delimitter is comma.
	*/
	
	private void checkIfCSVFile()
	{
		if(fullPath.substring(fullPath.length()-3,fullPath.length()).equalsIgnoreCase("csv"))
			isCSV = true;
		
		else
			isCSV = false;
	}
	
	/**
	invokes the function knnClassifier in the class KNNClassifier
	*/
	
	private void knnInvoker() throws IOException
	{
		
		String filePath = new String();
		int noOfNeigh;
		
		double values[] = new double[noOfAttrib];
		
		sendMessage2Client("Enter no of neighbours for the KNN classifier");
		noOfNeigh = Integer.parseInt(receiveMessageFromClient());
		
		for(int i = 0; i < noOfAttrib; i++)
			values[i] = Double.parseDouble(receiveMessageFromClient());
		
		KNNClassifier classifier = new KNNClassifier();
		
		Object obj = classifier.knnClassifierFn(datasetName, delimitter,noOfNeigh, noOfAttrib,values);
		sendMessage2Client(obj.toString());
	}
	
	/**
	invokes the function randomForestClassifier in the class RandomForestClassifier
	*/
	
	private void randomForestInvoker() throws IOException
	{
		
		String filePath = new String();
		int noOfTrees;
		
		double values[] = new double[noOfAttrib];
		
		sendMessage2Client("Enter no of trees for random forest classifier");
		noOfTrees = Integer.parseInt(receiveMessageFromClient());
		
		for(int i = 0; i < noOfAttrib; i++)
			values[i] = Double.parseDouble(receiveMessageFromClient());
		
		RandomForestClassifier classifier = new RandomForestClassifier();
		
		Object obj = classifier.randomForestClassifier(datasetName, delimitter,noOfTrees, noOfAttrib,values);
		sendMessage2Client(obj.toString());
	}
	
	/**
	Collects the information about the dataset which the client has sent
	*/
	
	private void requestInfoAbtDataset() throws IOException
	{
		checkIfCSVFile();
		
		if(isCSV == true)
			delimitter = ",";
			
		else
		{
			sendMessage2Client("Enter delimitter that is used in the dataset");
			delimitter = receiveMessageFromClient();
		}
		
		sendMessage2Client("Enter no of attributes in dataset");
		noOfAttrib = Integer.parseInt(receiveMessageFromClient());

	}
	
	/**
	overriding run method from class Thread
	*/
	
	public void run()
	{
		int optionFrmClient = 0,ctr = 0;
		Scanner sc = new Scanner(System.in);
		String temp;
		char option;
		
		try
		{
			
			messageFrmClient = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
			message2Client = new PrintWriter(new OutputStreamWriter(myClientSocket.getOutputStream()));
			fileFrmClient = new ReceiveFileFromClient();
			
			sendMessage2Client("Welcome to Data Mining server.... To start with you must upload text dataset");
			requestTransferDataset();
			sendMessage2Client("Transfer successful");
			requestInfoAbtDataset();
			
			do
			{
			sendMessage2Client("Press 1 for KNN classifier 2 for random forest classifier 3 to exit");
			optionFrmClient = Integer.parseInt(receiveMessageFromClient());
				
			switch(optionFrmClient)
			{
				case 1: 
					knnInvoker();
					break;
				
				case 2: 
					randomForestInvoker();
					break;
				
				case 3: 
					sendMessage2Client("Thank you for availing Data Mining services... bye");
					myClientSocket.close();
					break;
			
				default: 
					sendMessage2Client("Wrong input for option...");
					break;
			}
			
			sendMessage2Client("Do you wish to continue.(y/n)");
			option = receiveMessageFromClient().charAt(0); 
			
			}while(option == 'y' || option == 'Y');
			
			if(myClientSocket.isClosed() == false)
				myClientSocket.close();
		}
		catch (InputMismatchException imp)
		{
			sendMessage2Client("Incorrect input.");
		}
		
		catch (ArrayIndexOutOfBoundsException aie)
		{
			sendMessage2Client("Enter the correct no of attributes");
		}
		catch (UnknownHostException uhe)
		{
			sendMessage2Client("unknown host...");
		}
		catch (NumberFormatException nfe)
		{
			sendMessage2Client("Enter a valid integer as input");
		}
		catch (IOException iep)
		{
			sendMessage2Client("I/O exception occured at the server side....");
		}
			
	}
}