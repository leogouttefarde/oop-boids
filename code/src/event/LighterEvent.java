package event;

import element.Lighter;
import java.util.Random;

/**
 * Evénement Lighter :
 * Change la teinte des Boid de type Lighter à intervalles aléatoires.
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class LighterEvent extends Event {

	/**
	 * Crée un événement Lighter à la date donnée
	 * 
	 * @param date	Date
	 */
	public LighterEvent(long date) {
		super(date);
	}

	/* (non-Javadoc)
	 * @see event.Event#execute()
	 */
	public void execute() {
		Lighter.Event();

		Random rand = new Random();
		int next = rand.nextInt(10);

		EventManager.Get().addEvent(new LighterEvent(getDate() + next));
	}
}

