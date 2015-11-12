import java.util.PriorityQueue;

public class EventManager {
	
	private long currentDate;
	
	private PriorityQueue<Event> events;
	
	public EventManager() {
		events = new PriorityQueue<Event>();
		currentDate = 1;
	}
	
	public void addEvent(Event e) {
		events.add(e);
	};

	public void next() {
		System.out.println("Next... Current date : " + currentDate);
		Event d = events.poll();
		while (d != null && d.getDate() <= currentDate){
			d.execute();
			d = events.poll();
		}
		if (d != null){
			events.add(d);
		}
		currentDate++;
	};
	
	public boolean isFinished() {
		return events.isEmpty();
	};
	
	public void restart() {
		currentDate = 1;
		events.clear();
	};
	
}
