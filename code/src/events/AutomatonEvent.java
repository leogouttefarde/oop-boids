package events;

import groups.Automaton;

public class AutomatonEvent extends Event {
	
	private Automaton automaton;

	public AutomatonEvent(long date, Automaton automaton) {
		super(date);
		this.automaton = automaton;
	}

	public void execute() {
		automaton.generate();
		EventManager.Get().addEvent(new AutomatonEvent(getDate() + 1, automaton));
	}
}
