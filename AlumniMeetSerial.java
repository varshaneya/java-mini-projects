package alumniMeet;

import java.util.*;

public class AlumniMeetSerial {
	private AlumnusRecord[] alumni;
	
	public void runAlumniMeet(int N,int b,int T) {
		Random rNum = new Random();
		rNum.setSeed(System.nanoTime());
		
		int rand1,rand2;
		alumni = new AlumnusRecord[N];
		
		for(int i=0;i<N;i++) 
			alumni[i] = new AlumnusRecord(rNum.nextInt(b),N,i);
		
		try {
			Stopwatch s = new Stopwatch(T);//stopwatch started
			while(true) {
				rand1 = rNum.nextInt(N);
				rand2 = rNum.nextInt(N);
				while(rand1 == rand2) {
					rand2 = rNum.nextInt(N);
				}
				alumni[rand1].addHS(rand2);
				alumni[rand2].addHS(rand1);
				s.elapsedTime();
			}
		}
		catch(TimerException e) {	
		}
		
		System.out.println("\n\nStatistics of handshakes of alumni in " + T + " milliseconds\n");
		for(int i=0;i<N;i++)
		System.out.println("Alumnus-"+(i+1)+" have handshaked with a total of " + alumni[i].getTotNumHS()+ " alumni and " + alumni[i].getHSWithOthers(alumni)+" from other batches");
	}

	public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
		int N, b,T;
		
		System.out.println("Welcome to alumni meet\n");
		
		try {
		System.out.println("Enter the number of alumni: ");
		N = sc.nextInt();
		
		if(N<=0)
			throw new IllegalArgumentException("Number of alumni cannot be negative: ");
		
		System.out.println("Enter the number of batches: ");
		b = sc.nextInt();
		
		if(b<=0)
			throw new IllegalArgumentException("Number of batches cannot be negative: ");
		
		System.out.println("Enter the time for all handshake to happen (ms): ");
		T = sc.nextInt();
		
		if(T<=0)
			throw new IllegalArgumentException("Time of handshake cannot be negative: ");
		
		AlumniMeetSerial meet = new AlumniMeetSerial();
		meet.runAlumniMeet(N, b, T);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		finally {
		sc.close();
		}
		
	}
}
