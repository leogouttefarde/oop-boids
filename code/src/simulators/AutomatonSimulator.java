package simulators;

import groups.Automaton;
import events.AutomatonEvent;
import events.EventManager;
import java.awt.Color;
import gui.GUISimulator;
import gui.Simulable;
import gui.Rectangle;

public abstract class AutomatonSimulator implements Simulable {

	private final int BORDER = 10;
	private final int DIM = 12;
	private final int SIZE = DIM - 2;

	protected GUISimulator gui;
	protected Automaton automaton;
	protected int cells[][];

	public AutomatonSimulator(GUISimulator gui, Automaton automaton) {
		this.gui = gui;
		this.automaton = automaton;

		EventManager.Get().addEvent(new AutomatonEvent(1, automaton));
	}

	protected Rectangle viewCell(int x, int y, Color col) {
		return new Rectangle(BORDER + x * DIM, BORDER + y * DIM, col, col, SIZE);
	}

	protected abstract Color getCellColor(int x, int y);

	protected void updateFrame() {
		cells = automaton.getCells();

		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				gui.addGraphicalElement(viewCell(x, y, getCellColor(x, y)));
			}
		}
	}

	@Override 
	public void next() {
		EventManager.Get().next();
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
