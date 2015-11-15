package event;

/**
 * Evénement affichant un message
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class MessageEvent extends Event {

	private String message;

	/**
	 * Crée un événement affichant un message à la date donnée.
	 * 
	 * @param date		Date
	 * @param message	Message
	 */
	public MessageEvent(int date, String message) {
		super(date);
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see event.Event#execute()
	 */
	public void execute() {
		System.out.println(this.getDate() + this.message);
	}
}

