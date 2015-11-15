package event;

/**
 * Evénement abstrait gérable par l'EventManager
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public abstract class Event implements Comparable<Event> {
	
	private long date;
	
	/**
	 * Crée un événement à traiter à une date précise
	 * 
	 * @param date	Date
	 */
	public Event(long date) {
		this.date = date;
	}


	/**
	 * Getter de la date
	 * 
	 * @return	Date
	 */
	public long getDate() {
		return date;
	}
	
	/**
	 * Traitant de l'événement
	 */
	public abstract void execute();

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
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
