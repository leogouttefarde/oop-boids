package event;

import java.util.PriorityQueue;

/**
 * Gestionnaire d'événements : singleton
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class EventManager {
	
	private long currentDate;
	private PriorityQueue<Event> events;
	
	/**
	 * Instance du singleton
	 */
	private static final EventManager instance = new EventManager();


	/**
	 * Constructeur privé du gestionnaire d'événements
	 */
	private EventManager() {
		events = new PriorityQueue<Event>();
	}


	/**
	 * Retourne l'instance du gestionnaire d'événements
	 * 
	 * @return	Gestionnaire d'événements
	 */
	public static EventManager GetInstance() {
		return instance;
	}

	/**
	 * Retourne l'instance du gestionnaire d'événements
	 * 
	 * @return	Gestionnaire d'événements
	 */
	public static EventManager Get() {
		return GetInstance();
	}

	/**
	 * Ajoute un événement
	 * 
	 * @param e	Evénement à ajouter
	 */
	public void addEvent(Event e) {
		events.add(e);
	};

	/**
	 * Avance la date
	 */
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

	/**
	 * Indique s'il n'y a plus d'événements à traiter
	 * 
	 * @return	Vrai s'il n'y en a plus, faux sinon
	 */
	public boolean isFinished() {
		return events.isEmpty();
	};
	
	/**
	 * Réinitialise le gestionnaire d'événements
	 */
	public void restart() {
		events.clear();
		currentDate = 0;
	};
}
