import java.util.PriorityQueue;

public class EventManager {
	
	private long currentDate;
	private PriorityQueue<Event> events;
	private static EventManager instance = new EventManager();

	private EventManager() {
		events = new PriorityQueue<Event>();
		currentDate = 0;
	}

	public static EventManager GetInstance() {
		return instance;
	}

	public static EventManager Get() {
		return GetInstance();
	}

	public void addEvent(Event e) {
		events.add(e);
	};

	public void next() {
		currentDate++;
		// System.out.println("Next... Current date : " + currentDate);
		Event d = events.poll();

		while (d != null && d.getDate() <= currentDate) {
			d.execute();
			d = events.poll();
		}

		if (d != null) {
			events.add(d);
		}
	};

	public boolean isFinished() {
		return events.isEmpty();
	};
	
	public void restart() {
		currentDate = 1;
		events.clear();
	};
}
