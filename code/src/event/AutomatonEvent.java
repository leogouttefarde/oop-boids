package event;

import group.Automaton;

/**
 * Evénement d'automate
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class AutomatonEvent extends Event {
	
	private Automaton automaton;

	/**
	 * Crée un événement d'automate
	 * 
	 * @param date		Date
	 * @param automaton	Automate
	 */
	public AutomatonEvent(long date, Automaton automaton) {
		super(date);
		this.automaton = automaton;
	}

	/* (non-Javadoc)
	 * @see event.Event#execute()
	 */
	public void execute() {
		automaton.generate();
		EventManager.Get().addEvent(new AutomatonEvent(getDate() + 1, automaton));
	}
}
