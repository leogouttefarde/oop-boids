package events;

import java.util.PriorityQueue;

public class EventManager {
	
	private long currentDate;
	private PriorityQueue<Event> events;
	private static EventManager instance = new EventManager();

	private EventManager() {
		events = new PriorityQueue<Event>();
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
		Event d = events.poll();

		currentDate++;
		
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
		events.clear();
		currentDate = 0;
	};
}
