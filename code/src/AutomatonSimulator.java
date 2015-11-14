import gui.GUISimulator;
import gui.Simulable;

public abstract class AutomatonSimulator implements Simulable {

	protected GUISimulator gui;
	protected Automaton automaton;

	public AutomatonSimulator(GUISimulator gui, Automaton automaton) {
		this.gui = gui;
		this.automaton = automaton;
		
		EventManager.Get().addEvent(new AutomatonEvent(1, automaton));
	}

	protected abstract void updateFrame();

	@Override 
	public void next() {
		EventManager.Get().next();
		System.out.println(automaton);
		gui.reset();
		updateFrame();
	}

	@Override
	public void restart() {
		automaton.reset();
		gui.reset();
		updateFrame();
	}
}
