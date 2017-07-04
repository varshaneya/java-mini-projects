/**
This is program at the server side that has main() method and spawns the thread of the Server class.
@author varshaneya
*/

import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class ServerMain
{
	
	public static void main(String args[]) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		ServerSocket myServer=null;
		
		try
		{
			myServer = new ServerSocket(5000);
			
			
			while(true)
			{
				Server myThreadServer = new Server(myServer.accept());
				myThreadServer.start();
			}
			
		}
		
		catch (BindException be)
		{
			be.printStackTrace();
		}
	}

}