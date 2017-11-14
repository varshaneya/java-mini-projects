package alumniMeet;

import java.util.*;

public class AlumniMeetParallel{
	private AlumnusRecord[] alumni = null;
	private ArrayList<AlumnusRecord> freePool;
	
	public void runAlumniMeet(int N,int b,int T) {
		alumni = new AlumnusRecord[N];
		freePool = new ArrayList<AlumnusRecord>(N);
		
		Random rNum = new Random(); 
		rNum.setSeed(System.nanoTime());
		int i1,i2;
		AlumnusRecord alumnus1,alumnus2;
		
		for(int i=0;i<N;i++) { 
			alumni[i] = new AlumnusRecord(rNum.nextInt(b),N,i);
			freePool.add(alumni[i]);
		}
		try {
			Stopwatch s = new Stopwatch(T);//stopwatch started
			while(true) {
				i1 = rNum.nextInt(freePool.size());
				alumnus1 = freePool.get(i1);
				freePool.remove(i1);
				i2 = rNum.nextInt(freePool.size());
				alumnus2 = freePool.get(i2);
				freePool.remove(i2);
				new RunThreads(alumnus1, alumnus2);
				freePool.add(alumnus1);
				freePool.add(alumnus2);
				s.elapsedTime();
			}
		}
		catch(TimerException e) {	
			while(Thread.activeCount() > 1 );
		}
		
		System.out.println("\n\nStatistics of handshakes of alumni in " + T + " milliseconds\n");			
		for(int i=0;i<N;i++)
		System.out.println("Alumnus-"+(i+1)+" have handshaked with a total of " + alumni[i].getTotNumHS() + " alumni and " + alumni[i].getHSWithOthers(alumni)+" from other batches");
		
		System.out.println("Total threads spawned are "+RunThreads.thCount);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N=0, b=0,T = 0;
		try {
		System.out.println("Welcome to alumni meet\n");
		
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
		
		AlumniMeetParallel meet = new AlumniMeetParallel();
		meet.runAlumniMeet(N, b, T);
		}
		catch(IllegalArgumentException r) {
			System.out.println(r);
		}
		finally {
			sc.close();
		}
	}
}
