
public abstract class Event implements Comparable<Event> {
	
	private long date;
	
	public Event (long date) {
		this.date = date;
	}

	public long getDate() {
		return date;
	}
	
	public abstract void execute();
	
	public int compareTo(Event e) {
		long diff = this.date - e.getDate();

		if (diff < 0) {
			return -1;
		}
		else {
			return (diff > 0) ? 1 : 0;
		}
	}
}
