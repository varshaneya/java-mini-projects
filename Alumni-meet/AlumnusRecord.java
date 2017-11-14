package alumniMeet;
import java.util.*;

public class AlumnusRecord {
	protected int batch;
	protected ArrayList<Integer> handshakes; 
	protected int noOfPpl;
	protected int name;
	
	public AlumnusRecord(int b,int ppl,int name) {
		this.batch = b;
		this.noOfPpl = ppl;
		this.handshakes = new ArrayList<Integer>();
		this.name = name;
	}
	
	public int getName() {
		return this.name;
	}
	
	public int getBatch(){
		return this.batch; 
	}
	
	public void addHS(Integer handshake) {
	//	if (handshakes.contains(handshake) == false)
			synchronized(this) {
				handshakes.add(handshake);
			}
	}
	
	public int getTotNumHS() {
		return this.handshakes.size();
	}
	
	public int getHSWithOthers(AlumnusRecord[] alumni) {
		int count = 0;
		
		for(Integer i:handshakes)
			if(i!=null && this.batch != alumni[i].getBatch())
				++count;
		return count;
	}

}
