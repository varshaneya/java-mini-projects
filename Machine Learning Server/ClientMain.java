/**
This is program at the client side that has main() method and calls the clientProg() in Client class.
@author varshaneya
*/
import java.io.*;
import java.util.*;
import java.lang.*;
import java.net.*;

public class ClientMain
{
	public static void main(String args[]) throws IOException, ArrayIndexOutOfBoundsException
	{
		char option;
		Scanner scStr = new Scanner(System.in);
		String host = null;
		
		do
		{
			try
			{
				
				if(args.length == 0 && host == null)
					throw new ArrayIndexOutOfBoundsException();
				
				else if(args.length != 0)
					host = args[0];
				
				Client newClient = new Client(host);
				option = newClient.clientProg();
			}
			
			catch (ArrayIndexOutOfBoundsException aibe)
			{
				System.err.println("Enter a valid IP address for the server");
				host = scStr.nextLine();
				option = 'y';
			}
			
			catch(ConnectException ce)
			{
				System.out.println("Sorry... server is not running... try after sometime...");
				option = 'n';
			}
			
			catch(IOException iep)
			{
				iep.printStackTrace();
				option = 'y';
			}
			
		}while(option == 'y' || option == 'Y');
	}
}