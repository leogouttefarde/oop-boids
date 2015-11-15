package event;

import group.Balls;

/**
 * Evénement de Balls
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class BallsEvent extends Event {

	private Balls balls;

	/**
	 * Crée un événement de Balls
	 * 
	 * @param date	Date
	 * @param balls	Balls
	 */
	public BallsEvent(long date, Balls balls) {
		super(date);
		this.balls = balls;
	}

	/* (non-Javadoc)
	 * @see event.Event#execute()
	 */
	public void execute() {
		balls.translateBalls();
		EventManager.Get().addEvent(new BallsEvent(getDate() + 1, balls));
	}
}

