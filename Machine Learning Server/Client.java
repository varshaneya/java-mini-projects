/**
This file contains class Client which take input from the user and send it to the server. 
It displays the messages sent by the server to the client
The classifiers implemented are k-nearest neighbours and Random forest.
Transfer of dataset is also offered by this class
@author varshaneya
*/

import java.net.*;
import java.io.*;
import java.util.*;
import ML.*;

public class Client 
{
	/**
	private members of the class
	*/
	
	private BufferedReader responseFrmServer = null;
	private PrintWriter response2Server = null;
	private InetAddress serverAddress = null;
	private Socket clientSocket = null;
	private String datasetName = null;
	private String filName = null;
	private SendFile2Server newDataset = null;
	private boolean isCSV;
	private int noOfAttributes;
	
	/**
	Constructor of the class
	*/
	
	public Client(String host) throws IOException,UnknownHostException
	{
		serverAddress = InetAddress.getByName(host);
		clientSocket = new Socket(serverAddress, 5000);
		responseFrmServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		response2Server = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	}
	
	/**
	receives messages from the server that are sent and displays them on the screen
	*/
	
	private void messageFrmServer() throws IOException 
	{
		System.out.println("\n" + responseFrmServer.readLine());
	}
	
	/**
	sends user input to the server. 
	This function is overloaded to enable user inputs of  different datatypes to be sent
	*/
	
	private void sendMessage2Server(String message)
	{
		response2Server.println(message);
		response2Server.flush();
	}
	
	private void sendMessage2Server(char message)
	{
		response2Server.println(message);
		response2Server.flush();
	}
	
	private void sendMessage2Server(int message)
	{
		response2Server.println(message);
		response2Server.flush();
	}
	
	/**
	Inititates the upload and uploads the dataset to the server.
	*/
	
	private void uploadDataset() throws IOException
	{
		
		Scanner scStr = new Scanner (System.in);
		
		messageFrmServer();
		datasetName = scStr.nextLine();
		sendMessage2Server(datasetName);
		
		messageFrmServer();
		filName = scStr.nextLine();
		sendMessage2Server(filName);
		
		SendFile2Server newDataset = new SendFile2Server(clientSocket,filName);
		newDataset.send2Server(filName);
	}
	
	/**
	Requests the user to give information about the dataset
	*/
	private void infoAbtDataset() throws IOException
	{
		Scanner scStr = new Scanner(System.in);
		Scanner scInt = new Scanner(System.in);
		
		checkIfCSVFile();
		
		if(isCSV == false)
		{
			messageFrmServer();//delimitter
			sendMessage2Server(scStr.nextLine().charAt(0));
		}
		
		messageFrmServer();//no of attributes
		noOfAttributes = scInt.nextInt();
		sendMessage2Server(noOfAttributes);
	}
	
	/**
	Takes the necessary inputs from the user KNN classifier and sends it to the server.
	*/
	
	private void knnClassifier() throws IOException
	{
		
		Scanner scStr = new Scanner(System.in);
		Scanner scInt = new Scanner(System.in);
		
		String values[] = new String[noOfAttributes];
		
		messageFrmServer();//no of neighbours
		sendMessage2Server(scInt.nextInt());
		
		System.out.println("Enter values of attributes for the test sample to be classified");
		
		for(int i = 0; i < noOfAttributes; i++)
		{
			values[i] = scStr.nextLine();
			sendMessage2Server(values[i]);
		}
		
		System.out.println("The test sample is classified as:");
		messageFrmServer();
	}
	
	/**
	Takes the necessary input from the user random forest classifier and sends it to the server.
	*/
	
	private void randomForestClassifier() throws IOException
	{
		
		Scanner scStr = new Scanner(System.in);
		Scanner scInt = new Scanner(System.in);
		
		String values[] = new String[noOfAttributes];
		
		messageFrmServer();//no of trees
		sendMessage2Server(scInt.nextInt());
		
		System.out.println("Enter values of attributes for the test sample to be classified");
		
		for(int i = 0; i < noOfAttributes; i++)
		{
			values[i] = scStr.nextLine();
			sendMessage2Server(values[i]);
		}
		
		System.out.println("The test sample is classified as:");
		messageFrmServer();
	}
	
	/**
	Checks if the uploaded dataset is a .CSV file so that the default delimitter is comma.
	*/
	
	private void checkIfCSVFile()
	{
		if(filName.substring(filName.length()-3,filName.length()).equalsIgnoreCase("csv"))
			isCSV = true;
		
		else
			isCSV = false;
	}
	
	/**
	invokes the other private function in this class to perform their alloted tasks.
	*/
	
	public char clientProg() throws SocketException,FileNotFoundException, IOException
	{
		Scanner scInt = new Scanner(System.in);
		Scanner scStr = new Scanner (System.in);
		int optionFrmClient;
		char option = 'n';
		
		try
		{
			Runtime.getRuntime().exec("clear");
			
			messageFrmServer();
			uploadDataset();
			messageFrmServer();
			
			infoAbtDataset();
			
			do
			{
				messageFrmServer();
				optionFrmClient = scInt.nextInt();
				sendMessage2Server(optionFrmClient);
		
				switch(optionFrmClient)
				{
					case 1: 
						knnClassifier();
						break;
				
					case 2: 
						randomForestClassifier();
						break;
						
					case 3: 
						messageFrmServer();
						clientSocket.close();
						throw new SocketException();
				
					default: 
						messageFrmServer();
						break;
				}
				
				messageFrmServer();//do you wish to continue
				option = scStr.nextLine().charAt(0);
				sendMessage2Server(option);
			
			}while(option == 'y' || option == 'Y');
		}	
		
		catch (InputMismatchException imp)
		{
			System.out.println("Incorrect input.");
		}
		
		catch (SocketException ske)
		{
			option = 'n';
		}
		
		catch(FileNotFoundException fe)
		{
			option = 'n';
		}
		
		catch(UnknownHostException uhe)
		{
			System.err.println("Error.Unknown host...");
		}
		
		catch (IOException iep)
		{
			System.out.println("I/O Exception from the client side");
			iep.printStackTrace();
		}
		
		return option;
	}
}