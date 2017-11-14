package alumniMeet;

public class RunThreads extends Thread{

	public static int thCount =0;
	private AlumnusRecord a1;
	private AlumnusRecord a2;

	public RunThreads(AlumnusRecord al1, AlumnusRecord al2) {
		this.a1 = al1;
		this.a2 = al2;
		start();
	}
	
	public void run() {
		++thCount;
		a1.addHS(a2.getName());
		a2.addHS(a1.getName());
	}
}
