package event;

import group.Boids;

/**
 * Evénement de Boids
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class BoidsEvent extends Event {

	private Boids boids;

	/**
	 * Crée un événement de Boids
	 * 
	 * @param date	Date
	 * @param boids	Boids
	 */
	public BoidsEvent(long date, Boids boids) {
		super(date);
		this.boids = boids;
	}

	/* (non-Javadoc)
	 * @see event.Event#execute()
	 */
	public void execute() {
		boids.update();
		EventManager.Get().addEvent(new BoidsEvent(getDate() + 1, boids));
	}
}

