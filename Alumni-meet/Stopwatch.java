package alumniMeet;

public class Stopwatch {
	private final long start;
	private double set; 
	
	private long getTime() {
		return System.nanoTime();
	}
	
	public Stopwatch(int set) {
		start = getTime();
		this.set= set;
	}
	public void elapsedTime()throws TimerException {//elapsed time in milliseconds
		if(((getTime()-start)/Math.pow(10, 6)) > set)
			throw new TimerException("Time up.");
	}
}
