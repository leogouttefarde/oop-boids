import gui.GUISimulator;
import gui.Simulable;

public abstract class AutomatonSimulator implements Simulable {

	protected GUISimulator gui;
	protected Automaton game;

	public AutomatonSimulator(GUISimulator gui, Automaton game) {
		this.gui = gui;
		this.game = game;
	}

	protected abstract void updateFrame();

	@Override 
	public void next() {
		game.generate();
		System.out.println(game);
		gui.reset();
		updateFrame();
	}

	@Override
	public void restart() {
		game.reset();
		gui.reset();
		updateFrame();
	}
}
