package alumniMeet;

public class TimerException extends RuntimeException{
		private static final long serialVersionUID = 7635698667618739506L;
		private String str;
		public TimerException(String str) {
			this.str = str;
		}
		public String toString() {
			return this.str;
		}
}
